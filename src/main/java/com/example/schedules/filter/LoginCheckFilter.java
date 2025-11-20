package com.example.schedules.filter;

import com.example.schedules.exception.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import jakarta.servlet.Filter;
import com.example.schedules.exception.ErrorResponse;

import java.io.IOException;

@Slf4j // 로그 사용 가능하게 해주는 어노테이션
public class LoginCheckFilter implements Filter {
    //로그인 체크를 하지 않을 URI리스트
    private static final String[] whitelist = {"/", "/users", "/users/login", "/users/*"};
    /*
     * URI와 URL의 차이
     * URI는 리소스를 식별하는 '식별자'이고, URL은 그 리소스의 '위치'를 나타내는 주소입니다.
     * 따라서 URI는 URL보다 상위 개념이며, 모든 URL은 URI이지만 모든 URI가 URL은 아닙니다.
     * URI (Uniform Resource Identifier)
     * 개념: 웹 리소스(문서, 이미지, 서비스 등)를 식별하는 고유한 문자열입니다.
     * 목적: 리소스의 '식별'에 초점을 맞춥니다.
     * 범위: URL과 URN(Uniform Resource Name)을 모두 포함하는 더 넓은 개념입니다.
     * URL (Uniform Resource Locator)
     * 개념: 인터넷 상의 특정 리소스에 접근하기 위한 '위치'를 나타내는 주소입니다.
     * 목적: 리소스의 '위치'에 초점을 맞춥니다.
     * 특징: 어떤 리소스를 찾고 액세스해야 하는지에 대한 정보를 포함합니다.
     * 예시: {Link: https://www.google.com}과 같은 웹 주소는 구체적인 위치를 가리키므로 URL입니다.
    */

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request; //http 요청 받기
        String requestURI = httpRequest.getRequestURI(); // 요청 URL

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            //화이트 리스트가 아니면 로그인 체크 필요
            if (isLoginCheckPath(requestURI)) {
                HttpSession session = httpRequest.getSession(false);// 기존 세션만 가져온다.
                if (session == null || session.getAttribute("loginUser") == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    // 에러 응답 JSON 생성
                    ErrorResponse errorResponse = new ErrorResponse(
                            ErrorCode.NOT_LOGGED_IN.name(),
                            ErrorCode.NOT_LOGGED_IN.getMessage()
                    );

                    ObjectMapper objectMapper = new ObjectMapper();
                    String json = objectMapper.writeValueAsString(errorResponse);

                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpResponse.setContentType("application/json;charset=UTF-8");
                    httpResponse.getWriter().write(json);
                    return; //미인증 사용자는 다음으로 진행하지 않고 종료
                }
            }
            chain.doFilter(request, response); //로그인 되어 있거나 화이트리스트면  다음 필터/컨트롤러 진행
        } catch (Exception e) {
            log.error("Filter error: ", e);
            throw e; //예외 로깅 가능 하지만, 톰캣까지 예외를 보내주어야 함
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    //화이트 리스트의 경우 인증 체크X
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }
}