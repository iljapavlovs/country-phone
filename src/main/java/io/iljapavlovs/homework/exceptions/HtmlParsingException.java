package io.iljapavlovs.homework.exceptions;

import static java.lang.String.format;

public class HtmlParsingException extends RuntimeException {

  public HtmlParsingException(String url) {
    super(format("Exception occurred while parsing html at %s", url));
  }
}
