package com.example.schedules.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 스프링 설정 클래스임을 알려준다.
public class FilterConfig {
    @Bean //빈 등록
    public FilterRegistrationBean<LoginCheckFilter> loginCheckFilter() {
        FilterRegistrationBean<LoginCheckFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginCheckFilter());//필터 등록
//        filterFilterRegistrationBean.setOrder(2);//필터 실행 순서 설정할 수 있다.
        filterFilterRegistrationBean.addUrlPatterns("/*");// 모든 요청 URL에 필터 적용

        return filterFilterRegistrationBean;// 필터 설정 반환
    }
}
