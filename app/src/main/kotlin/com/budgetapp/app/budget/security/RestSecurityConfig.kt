package com.budgetapp.app.budget.security


import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.io.IOException
import java.sql.Timestamp
import java.util.*
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class RestSecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    var objectMapper: ObjectMapper? = null


    @Bean
    fun tokenAuthenticationFilter(): com.budgetapp.app.budget.security.TokenFilter {
        return TokenFilter()
    }

    @Bean
    fun restAuthenticationEntryPoint(): AuthenticationEntryPoint {
        println("is this working security config")
        return object : AuthenticationEntryPoint {
            @Throws(IOException::class, ServletException::class)
            override fun commence(httpServletRequest: HttpServletRequest?, httpServletResponse: HttpServletResponse,
                                  e: AuthenticationException?) {
                val errorObject: MutableMap<String, Any> = HashMap()
                val errorCode = 401
                errorObject["message"] = "Access Denied"
                errorObject["error"] = HttpStatus.UNAUTHORIZED
                errorObject["code"] = errorCode
                errorObject["timestamp"] = Timestamp(Date().time)
                httpServletResponse.contentType = "application/json;charset=UTF-8"
                httpServletResponse.status = errorCode
                httpServletResponse.writer.write(objectMapper!!.writeValueAsString(errorObject))
            }
        }
    }

    @Throws(Exception::class)
    protected override fun configure(http: HttpSecurity) {
        http.cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf()
                .disable().formLogin().disable().httpBasic().disable().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint()).and().authorizeRequests()
//                .antMatchers(HttpMethod.GET).access("#oauth2.hasScope('get')")
                .antMatchers("/api/budget**").permitAll()
                .anyRequest().authenticated()
        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }


    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = Arrays.asList("*")
        configuration.allowedMethods = Arrays.asList("GET", "POST")
        configuration.allowCredentials = true
        //the below three lines will add the relevant CORS response headers
        configuration.addAllowedOrigin("*")
        configuration.addAllowedHeader("*")
        configuration.addAllowedMethod("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
}