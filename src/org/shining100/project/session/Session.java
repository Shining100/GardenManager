package org.shining100.project.session;

import java.util.Map;

/**
 * Created by yangguang on 14-3-6.
 */
public interface Session {
    public String process(Map<String, String> requestMap);

    public boolean isComplete();
}
