package com.episen.ms.setting;

import ch.qos.logback.core.util.Loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class InfraSettings {

	
	public static KeyPair keyPairLoader(){
		String current = null;
		Class c = null;
		try {
			try {
				c = Class.forName("com.episen.ms.setting.InfraSettings");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			current = new java.io.File( "/ms-security.jar/keys" ).getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Current dir:"+current);
		try(InputStream is = InfraSettings.class.getResourceAsStream("/keys/server.p12")) {

			KeyStore kstore = KeyStore.getInstance("PKCS12");
			kstore.load(is, "episen".toCharArray());
			
			Key key = kstore.getKey("episen", "episen".toCharArray());
			
			Certificate certificat = kstore.getCertificate("episen");
			
			return new KeyPair(certificat.getPublicKey(), (PrivateKey)key);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
