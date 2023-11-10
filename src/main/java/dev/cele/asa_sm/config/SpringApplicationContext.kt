package dev.cele.asa_sm.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Configuration

@Configuration
internal open class SpringApplicationContext : ApplicationContextAware {

    override fun setApplicationContext(context: ApplicationContext) {
        Companion.context = context
    }

    companion object {
        @JvmStatic
        fun<T> autoWire(java: Class<T>): T {
            return context!!.getBean(java)
        }

        @JvmStatic
        fun<T> autoWire(qualifier: String): T {
            return context!!.getBean(qualifier) as T
        }

        @JvmStatic
        var context: ApplicationContext? = null
    }
}