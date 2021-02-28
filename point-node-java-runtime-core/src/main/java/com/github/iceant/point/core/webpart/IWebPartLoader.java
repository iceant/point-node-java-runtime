package com.github.iceant.point.core.webpart;

import java.util.Map;

public interface IWebPartLoader {
    WebPart load(String name, Map args);
}
