package com.veronica.httpserver.http;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpRequest  extends HttpMessage {

    private HttpMethod method;
    private String requestTarget;
    private String httpVersion;
}
