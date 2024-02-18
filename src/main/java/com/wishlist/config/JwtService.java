package com.wishlist.config;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService {

	// JWT secret
	private static final String SECRET_KEY = "2F423F4528482B4D6251655468576D5A7133743677397A24432646294A404E635266556A586E327235753778214125442A472D4B6150645367566B5970337336";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	// Method to generate JWT with additional claims
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		Calendar calendar = Calendar.getInstance(); // Get an instance of Calendar
		calendar.add(Calendar.WEEK_OF_YEAR, 1); // Add one week to the current date

		return Jwts.builder().setClaims(extraClaims) // Set additional claims
				.setSubject(userDetails.getUsername()) // Set subject to username
				.setIssuedAt(new Date(System.currentTimeMillis())) // Set issued at to current time
				.setExpiration(calendar.getTime()) // Set expiration time to one week from now
				.signWith(getSigningKey(), SignatureAlgorithm.HS512) // Sign the JWT
				.compact(); // Build the JWT
	}

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

		final Claims claims = extractAllClaims(token);

		return claimsResolver.apply(claims);

	}

	private Claims extractAllClaims(String token) {

		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();

	}

	private Key getSigningKey() {

		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

		return Keys.hmacShaKeyFor(keyBytes);
	}
}
