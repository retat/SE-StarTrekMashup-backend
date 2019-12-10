package edu.wpi.cs.indefatigable;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.cs.indefatigable.http.AppendSegmentToPlaylistRequest;
import edu.wpi.cs.indefatigable.http.AppendSegmentToPlaylistResponse;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class AppendSegmentToPlaylistTest extends LambdaTest {

    @Test
    public void testAppendToPlaylist() {
    	
        AppendSegmentToPlaylistRequest req = new AppendSegmentToPlaylistRequest("f928b4c9-01be-486b-a2d2-b00bed96d984", "fe2d9e10-1ecf-443c-be0d-1e1b826237a1");    
        AppendSegmentToPlaylistResponse res = new AppendSegmentToPlaylistHandler().handleRequest(req, createContext("append"));
        Assert.assertEquals(200, res.statusCode);
    }
    
}