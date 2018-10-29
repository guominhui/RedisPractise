package com.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CopyUtil {

	public static <T extends Object> T depthClone(T srcObject){
		T cloneObj = null;
		ByteArrayOutputStream out=null;
		ByteArrayInputStream in=null;
		ObjectOutputStream oo=null;
		ObjectInputStream oin=null;
		try{
			out=new ByteArrayOutputStream();
			oo=new ObjectOutputStream(out);
			oo.writeObject(srcObject);
			in=new ByteArrayInputStream(out.toByteArray());
			oin=new ObjectInputStream(in);
			cloneObj=(T) oin.readObject();
		}catch (Exception e) {
			System.out.println("深度拷贝出现异常");
		}finally{
			try {
				oin.close();
				in.close();
				oo.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return cloneObj;
	}
}
