package junit.test;

import org.junit.Test;

import com.example.utils.ImgUtil;

public class TestPicture {
	
	@Test
	public void testPicture() throws Exception {
//		ImgCompress imgCompress = new ImgCompress("G://3.jpg");
//		
		ImgUtil imgUtil = new ImgUtil("G://1.jpg");
		imgUtil.resize(600, 600, "G://", "new.png");
		
	}
}
