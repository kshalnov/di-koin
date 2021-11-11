package ru.gb.course1.dil

import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import kotlin.reflect.KClass

const val DEFAULT_DEPENDENCY_NAME = "default"
const val DI_FRAGMENT_TAG = "di_fragment_holder"

@SuppressLint("StaticFieldLeak")
object Di {

    private val dependenciesMap = HashMap<Qualifier, DependencyFabric>()

    data class Qualifier(
        private val clazz: KClass<*>,
        private val named: String
    )

    abstract class DependencyFabric(internal val qualifier: Qualifier) {
        abstract fun get(): Any
    }

    class Singleton(qualifier: Qualifier, private val creator: () -> Any) :
        DependencyFabric(qualifier) {
        private val dependency: Any by lazy { creator() }

        override fun get(): Any = dependency
    }

    class Fabric(qualifier: Qualifier, private val creator: () -> Any) :
        DependencyFabric(qualifier) {
        override fun get(): Any = creator()
    }

    fun add(dependency: DependencyFabric) {
        val qualifier = dependency.qualifier
        if (dependenciesMap.containsKey(qualifier)) throw IllegalStateException("DI dependency already exist")
        dependenciesMap[qualifier] = dependency
    }

    fun get(qualifier: Qualifier): Any {
        return dependenciesMap[qualifier]?.get()
            ?: throw IllegalStateException("DI graph error")
    }

}

inline fun <reified T : Any> singleton(
    named: String = DEFAULT_DEPENDENCY_NAME,
    noinline creator: () -> T
) =
    Di.add(Di.Singleton(Di.Qualifier(T::class, named), creator))

inline fun <reified T : Any> fabric(
    named: String = DEFAULT_DEPENDENCY_NAME,
    noinline creator: () -> T
) =
    Di.add(Di.Fabric(Di.Qualifier(T::class, named), creator))

inline fun <reified T> get(named: String = DEFAULT_DEPENDENCY_NAME): T {
    return Di.get(Di.Qualifier(T::class, named)) as? T
        ?: throw IllegalStateException("DI graph error")
}

inline fun <reified T> inject(named: String = DEFAULT_DEPENDENCY_NAME) = lazy {
    get<T>(named)
}

inline fun <reified T> inject(activity: FragmentActivity) = lazy {
    (activity.supportFragmentManager.findFragmentByTag(DI_FRAGMENT_TAG) as? DiHolderFragment)?.let {
        return@lazy it.dependency as T
    }
    val holderFragment = DiHolderFragment()
    val dependency = get<T>()
    holderFragment.dependency = dependency as Any
    activity.supportFragmentManager.beginTransaction()
        .add(holderFragment, DI_FRAGMENT_TAG)
        .commitAllowingStateLoss()
    return@lazy dependency
}
