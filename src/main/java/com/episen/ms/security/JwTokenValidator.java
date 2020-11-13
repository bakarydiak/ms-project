package com.episen.ms.security;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.episen.ms.model.UserContext;
import com.episen.ms.setting.InfraSettings;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwTokenValidator {

	private JWSVerifier verifier;
	
	@PostConstruct
	public void init(){
		
		verifier = new RSASSAVerifier((RSAPublicKey) InfraSettings.keyPairLoader().getPublic());
	}
	
	public String transforme(String token){
		
		try {
			
			SignedJWT signedJwt = SignedJWT.parse(token);
			System.out.println(signedJwt.getJWTClaimsSet().getSubject());
			if(!signedJwt.verify(verifier)){
				throw new RuntimeException("toto");
			}
			
			if(!validate(signedJwt.getJWTClaimsSet())){
				throw new RuntimeException("token cannot be verified. Invalide Token");
			}
			
			return signedJwt.getJWTClaimsSet().getClaim("role").toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean validate(JWTClaimsSet claims){
		return validateTokenExpiration(claims) && validateTokenIssuer(claims);
	}
	
	private boolean validateTokenExpiration(JWTClaimsSet claims){
		return Instant.now().isBefore(claims.getExpirationTime().toInstant());
	}
	
	private boolean validateTokenIssuer(JWTClaimsSet claims){
		return claims.getIssuer().equalsIgnoreCase("EPISEN-MEMBERSHIP") ? true : false;
	}
}
