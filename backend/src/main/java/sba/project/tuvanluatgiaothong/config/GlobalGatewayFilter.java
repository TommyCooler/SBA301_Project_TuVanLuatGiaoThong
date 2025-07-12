// package sba.project.tuvanluatgiaothong.config;


// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.cloud.gateway.filter.GatewayFilterChain;
// import org.springframework.cloud.gateway.filter.GlobalFilter;
// import org.springframework.core.Ordered;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.stereotype.Component;
// import org.springframework.web.server.ServerWebExchange;
// import reactor.core.publisher.Mono;
// import sba.project.tuvanluatgiaothong.dto.response.ApiResponse;
// import sba.project.tuvanluatgiaothong.service.IAuthenticationService;

// import org.springframework.http.server.reactive.ServerHttpResponse;
// import org.springframework.http.HttpStatus;
// import java.util.List;

// /*
//  * File: GlobalGatewayFilter.java
//  * Description: This class is used to filter the request before it goes to the endpoint.
//  * Filter: Jwt token validation, role validation.
//  *
//  * Version History:
//  * ----------------------------------------------------------------------------
//  * v1.0 - ?/2/2025 - Nguyen Tien Thuan - Initial configuration.
//  * v1.1 - ?/3/2025 - Nguyen Tien Thuan - Add role validation.
//  * ----------------------------------------------------------------------------
//  *
//  * Author(s): Nguyen Tien Thuan
//  * Last Modified: 26/3/2025
//  * Notes:
//  */
// @Component
// public class GlobalGatewayFilter implements GlobalFilter, Ordered {



//     /**
//      * Circular dependency bean issue fixing by moving IdentityClient to IdentityClientService,
//      * IdentityClientService as a middle to IdentityClient and this.
//      * &#064;Lazy annotation is used to resolve the circular dependency issue
//      * because Spring will delay initializing the bean until the first time it is used.
//      */

//     private IAuthenticationService authenticationService;

//     @Autowired private RouterValidator routerValidator;
//     @Autowired
//     private RoleRouterValidator roleRouterValidator;

//     /**
//      * This method is used to filter the request.
//      * If request endpoint matches with open enfpoints, then it will pass the request without any validation.
//      * If it does not match, filter will check the token in the request header,
//      * then validating roles before go to endpoint.
//      * @param exchange the current server exchange
//      * @param chain provides a way to delegate to the next filter
//      * @return Mono<Void> - it goes to the next filter or endpoint
//      */
//     @Override
//     public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//         ServerHttpRequest request = exchange.getRequest();
//         System.out.println("Request Path: " + request.getPath().value());
//         if(routerValidator.isSecured.test(request)) {
//             if(authMissing(request))
//                 return onError(exchange, HttpStatus.UNAUTHORIZED);
//             else {
//                 String token = request.getHeaders().getOrEmpty("Authorization").getFirst().substring(7);
//                 ApiResponse<List<String>> response = authenticationService.authenticateToken(token);
//                 if (response.getStatus().equals("invalid"))
//                     return onError(exchange, HttpStatus.UNAUTHORIZED);
//                     // Validate the role of the user to access the endpoint. HERE!!
//                 else {
//                     if(response.getDataResponse().contains("ROLE_USER") &&
//                        roleRouterValidator.isUserEndpoint(request.getPath().value())
//                     ){
//                         System.out.println("Role user is pass");
//                     }
//                     else if (response.getDataResponse().contains("ROLE_ADMIN") &&
//                         roleRouterValidator.isAdminEndpoint(request.getPath().value())
//                     ){
//                         System.out.println("Role admin is pass");
//                     }
//                     else {
//                         System.out.println("No role is pass");
//                         return onError(exchange, HttpStatus.FORBIDDEN);
//                     }
//                 }
//             }
//         }
//         return chain.filter(exchange);
//     }

//     /**
//      * Method is used to set the order of the filter.
//      * '-1' is the highest priority.
//      * @return int - order of the filter
//      */
//     @Override
//     public int getOrder() {
//         return -1;
//     }

//     /**
//      * Method is used to check the existing of token in the request header.
//      * @param request the current server request
//      * @return boolean - true if token is missing, false otherwise
//      */
//     private boolean authMissing(ServerHttpRequest request) {
//         return !request.getHeaders().containsKey("Authorization");
//     }

//     /**
//      * Method is used to set the error status code when the request is unauthorized or missing token.
//      * @param exchange the current server exchange
//      * @param httpStatus the status code of the error
//      * @return Mono<Void> - it is set completed response to return the error status code
//      */
//     private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
//         ServerHttpResponse response = exchange.getResponse();
//         response.setStatusCode(httpStatus);
//         return response.setComplete();
//     }
// }
