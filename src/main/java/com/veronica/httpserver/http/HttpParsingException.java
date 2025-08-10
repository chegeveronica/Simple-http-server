package com.veronica.httpserver.http;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpParsingException extends Exception {

    private final HttpStatusCode errorCode;

}
