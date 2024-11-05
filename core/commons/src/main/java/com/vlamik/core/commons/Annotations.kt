// ktlint-disable filename
@file:Suppress("MatchingDeclarationName", "Filename")
package com.vlamik.core.commons

@javax.inject.Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BackgroundDispatcher

@javax.inject.Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiUrl

@javax.inject.Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKeyHeader

@javax.inject.Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApiKey

@javax.inject.Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserEmail

@javax.inject.Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class UserPassword
