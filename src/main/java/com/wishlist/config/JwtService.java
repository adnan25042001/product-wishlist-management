package com.wishlist.config;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

//This is a Spring service class that handles JWT (JSON Web Token) operations.
@Service
@RequiredArgsConstructor
public class JwtService {

	// The secret key used for signing the JWT.
	@Value("${jwt.secret}")
	private String SECRET_KEY;

	// Method to extract the username from the JWT.
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// Method to generate a JWT for a user.
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	// Method to generate JWT with additional claims.
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		Calendar calendar = Calendar.getInstance(); // Get an instance of Calendar
		calendar.add(Calendar.WEEK_OF_YEAR, 1); // Add one week to the current date

		// Build and return the JWT.
		return Jwts.builder().setClaims(extraClaims) // Set additional claims
				.setSubject(userDetails.getUsername()) // Set subject to username
				.setIssuedAt(new Date(System.currentTimeMillis())) // Set issued at to current time
				.setExpiration(calendar.getTime()) // Set expiration time to one week from now
				.signWith(getSigningKey(), SignatureAlgorithm.HS512) // Sign the JWT
				.compact(); // Build the JWT
	}

	// Method to validate the JWT.
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	// Method to check if the JWT is expired.
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	// Method to extract the expiration date from the JWT.
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	// Method to extract a claim from the JWT.
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	// Method to extract all claims from the JWT.
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	// Method to get the signing key.
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
