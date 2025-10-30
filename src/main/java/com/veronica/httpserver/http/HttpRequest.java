package com.veronica.httpserver.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpRequest  extends HttpMessage {

    @Getter
    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;

    public void setMethod(String methodName) throws HttpParsingException {
        for(HttpMethod method : HttpMethod.values()){
            if (methodName.equals(method.name())){
                this.method = method;
                return;
            }
        }
        throw new HttpParsingException(HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED);

    }
}
