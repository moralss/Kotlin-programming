package com.budgetapp.app.budget.security
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
//import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


//@Slf4j
class TokenFilter : OncePerRequestFilter() {
    @Autowired
    var securityUtils: SecurityUtils? = null


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        println("is this working token filter")
        val path = request.requestURI
//        if ("/api/monthly".contains(path)) {
            val idToken = securityUtils!!.getTokenFromRequest(request)
            println(idToken)
            var decodedToken: FirebaseToken? = null
            if (idToken != null && !idToken.equals("undefined", ignoreCase = true)) {
                try {
                    decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken)
                } catch (e: FirebaseAuthException) {
//                    log.error("Firebase Exception:: ", e.localizedMessage)
                }
                if (decodedToken != null) {
                    println("hello decodedToken" + decodedToken.name)
                }
            }
            if (decodedToken != null) {
                val user = User()
                user.uid = decodedToken.uid
                user.name = decodedToken.name
                user.email = decodedToken.email
                user.picture = decodedToken.picture
                user.issuer = decodedToken.issuer
                user.isEmailVerified = decodedToken.isEmailVerified
                val authentication = UsernamePasswordAuthenticationToken(user,
                        decodedToken, null)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
//        }
            filterChain.doFilter(request, response)
        }
    }
