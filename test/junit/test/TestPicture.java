package junit.test;

import java.io.IOException;

import org.junit.Test;

import com.example.utils.CompressPic;

public class TestPicture {
	
	@Test
	public void testPicture() throws Exception {
//		ImgCompress imgCompress = new ImgCompress("G://3.jpg");
//		
//		imgCompress.resizeFix(600, 400);
		
		CompressPic pic = new CompressPic();
		pic.compressPic("G://1.jpg", "G://", null,"6.png",1000,2000,true);
		
	}
}
