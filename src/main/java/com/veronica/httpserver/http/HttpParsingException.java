package com.veronica.httpserver.http;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class HttpParsingException extends Exception {

    private final HttpStatusCode errorCode;

}
