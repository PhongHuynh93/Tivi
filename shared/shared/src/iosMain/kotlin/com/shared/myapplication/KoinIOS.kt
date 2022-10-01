package com.shared.myapplication

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.crashlytics.CrashlyticsLogWriter
import co.touchlab.kermit.crashlytics.setupCrashlyticsExceptionHook
import com.russhwolf.settings.AppleSettings
import com.russhwolf.settings.Settings
import com.shared.util.AppContext
import kotlinx.cinterop.ObjCClass
import kotlinx.cinterop.getOriginalKotlinClass
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module
import platform.Foundation.NSUserDefaults

@Suppress("OPT_IN_USAGE")
fun initKoinIos(
    userDefaults: NSUserDefaults,
    doOnStartup: () -> Unit
): KoinApplication {
    Logger.addLogWriter(CrashlyticsLogWriter(
        minSeverity = Severity.Verbose,
        minCrashSeverity = Severity.Warn,
        printTag = true
    ))
    setupCrashlyticsExceptionHook(Logger)
    return initKoin(
        module {
            single<AppContext> { }
            single<Settings> { AppleSettings(userDefaults) }
            single { doOnStartup }
        }
    )
}

actual val platformModule = module {
    single<DriverFactory> {
        DriverFactory()
    }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, parameter: Any): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, null) { parametersOf(parameter) }
}

fun Koin.get(objCClass: ObjCClass, qualifier: Qualifier?): Any {
    val kClazz = getOriginalKotlinClass(objCClass)!!
    return get(kClazz, qualifier, null)
}
