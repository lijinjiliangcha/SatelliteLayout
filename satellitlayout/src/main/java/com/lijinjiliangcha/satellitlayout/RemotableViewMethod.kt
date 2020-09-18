package com.lijinjiliangcha.satellitlayout

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class RemotableViewMethod(
    /**
     * @return Method name which can be called on a background thread. It should have the
     * same arguments as the original method and should return a [Runnable] (or null)
     * which will be called on the UI thread.
     */
    val asyncImpl: String = ""
)
