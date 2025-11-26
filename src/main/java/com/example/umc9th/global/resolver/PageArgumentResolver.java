package com.example.umc9th.global.resolver;

import com.example.umc9th.global.annotation.ValidPage;
import com.example.umc9th.global.apiPayload.code.GeneralErrorCode;
import com.example.umc9th.global.apiPayload.exception.GeneralException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class PageArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter){
        return parameter.hasParameterAnnotation(ValidPage.class)
                && parameter.getParameterType().equals(Integer.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mvContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String pageStr = webRequest.getParameter("page");

        if(pageStr == null){
            throw new GeneralException(GeneralErrorCode.PAGE_NOT_FOUND);
        }

        int page;

        try {
            page = Integer.parseInt(pageStr);
        } catch (NumberFormatException e) {
            throw new GeneralException(GeneralErrorCode.INVALID_PAGE);
        }

        if(page < 1){
            throw new GeneralException(GeneralErrorCode.INVALID_PAGE);
        }

        return page;
    }
}
