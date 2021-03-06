package edu.wpi.cs.indefatigable;

import org.junit.Assert;
import org.junit.Test;
import edu.wpi.cs.indefatigable.http.AppendSegmentToPlaylistRequest;
import edu.wpi.cs.indefatigable.http.AppendSegmentToPlaylistResponse;
import edu.wpi.cs.indefatigable.http.RemoveVideoFromPlaylistRequest;
import edu.wpi.cs.indefatigable.http.RemoveVideoFromPlaylistResponse;

public class RemoveSegmentFromPlaylistTest extends LambdaTest{
	
	@Test
	public void testRemoveSegmentFromPlaylist() {
        AppendSegmentToPlaylistRequest req = new AppendSegmentToPlaylistRequest("5459776a-f644-422f-916c-a15a0366d072", "32b7976c-cd4d-4fb3-805c-018053d1bf01");    
        AppendSegmentToPlaylistResponse res = new AppendSegmentToPlaylistHandler().handleRequest(req, createContext("append"));
        RemoveVideoFromPlaylistRequest remreq = new RemoveVideoFromPlaylistRequest("5459776a-f644-422f-916c-a15a0366d072", "32b7976c-cd4d-4fb3-805c-018053d1bf01");
        RemoveVideoFromPlaylistResponse remres = new RemoveVideoFromPlaylistHandler().handleRequest(remreq, createContext("remove"));
        remres.toString();
        RemoveVideoFromPlaylistResponse other = new RemoveVideoFromPlaylistResponse("uhoh", "playlist", 200, "nooooo");
        Assert.assertEquals(200, remres.statusCode);
        Assert.assertEquals(200, res.statusCode);
	}

}
