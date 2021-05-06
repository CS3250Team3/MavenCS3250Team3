package CS3250;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.List;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
 
/**
 * Class used to create users, encrypt passwords/usernames, generate salts for password encryption, and to authenticate users on login
 */
public class UserAuthenticator {
	
	/** 
	 * Creates a new user and adds it to the database
	 * @param username - Plaintext username
	 * @param password - Plaintext password
	 * @param data - UserData object used to connect to database and add user
	 * @throws NoSuchAlgorithmException - Throws if the algorithm to be used for hashing does not exist
	 * @throws InvalidKeySpecException - Throws if the key specification fed to the secret key factory is invalid
	 */
	public static void createUser(String username, String password, DataMan<User> data) throws NoSuchAlgorithmException, InvalidKeySpecException {
		User newUser = new User();
		
		// Generate encrypted user info
		newUser.setUsername(getEncryptedUsername(username));
		newUser.setSalt(generateSalt());
		newUser.setPassword(getEncryptedPassword(password, newUser.getSalt()));
		
		// Add user to database
		data.createEntry("CREATE_USER", newUser);
		return;
	}
	
	
	/** 
	 * Creates a new user but doesn't add it to the database
	 * @param username - Plaintext username
	 * @param password - Plaintext password
	 * @return User - User object containing username, password, and randomized salt
	 * @throws NoSuchAlgorithmException - Throws if the algorithm to be used for hashing does not exist
	 * @throws InvalidKeySpecException - Throws if the key specification fed to the secret key factory is invalid
	 */
	public static User createUser(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		User newUser = new User();
		
		// Generate encrypted user info
		newUser.setUsername(getEncryptedUsername(username));
		newUser.setSalt(generateSalt());
		newUser.setPassword(getEncryptedPassword(password, newUser.getSalt()));
	
		return newUser;
	}
 
	
	/** 
	 * Checks username and password against those stored in the database to see if the login is valid or not
	 * @param username - Plaintext username
	 * @param password - Plaintext password
	 * @param data - UserData object passed in from the GUI
	 * @return boolean - True if there exists a user with the same username and password, false if not
	 * @throws NoSuchAlgorithmException - Throws if the algorithm to be used for hashing does not exist
	 * @throws InvalidKeySpecException - Throws if the key specification fed to the secret key factory is invalid
	 */
	public static boolean authenticate(String username, String password, DataMan<User> data)
	   throws NoSuchAlgorithmException, InvalidKeySpecException {
		// Generate encrypted username
		byte[] encryptedUser = getEncryptedUsername(username);
		List<User> users = data.getEntries();
		User currentUser;
		
		// Iterate through all users
		for(int i = 0; i < users.size(); i++) {
			currentUser = users.get(i);
			// Use current user's salt to encrypt plaintext password, check if they match
			byte[] encryptedAttemptedPassword = getEncryptedPassword(password, currentUser.getSalt());
			if(Arrays.equals(encryptedAttemptedPassword, currentUser.getPassword()) && Arrays.equals(encryptedUser, currentUser.getUsername())) {
				return true;
			}
		}
		
		return false;
	 }
 
	
	/** 
	 * Hashes a plaintext username into a byte array using PBKDF2
	 * @param username - Plaintext username
	 * @return byte[] - Hashed username 
	 * @throws InvalidKeySpecException - Throws if the key specification fed to the secret key factory is invalid
	 * @throws NoSuchAlgorithmException - Throws if the algorithm to be used for hashing does not exist
	 */
	public static byte[] getEncryptedUsername(String username) throws InvalidKeySpecException, NoSuchAlgorithmException {
		String algorithm = "PBKDF2WithHmacSHA1"; // PBKDF2 with SHA-1 as the hashing algorithm
		byte[] pepper = {0,0,0,0,0,0,0,0}; // Use the same salt for every username for easy lookup
		
		// Tells key factory what type of key to make
		KeySpec spec = new PBEKeySpec(username.toCharArray(), pepper, 2, 160);
		
		// Instantiate factory with the algorithm to be used
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		
		// Return hashed username
		return f.generateSecret(spec).getEncoded();
	}
	
	
	/** 
	 * Hashes a plaintext password with PBKDF2 and a randomized salt into a byte array
	 * @param password - Plaintext password
	 * @param salt - Randomized byte array
	 * @return byte[] - Hashed password
	 * @throws InvalidKeySpecException - Throws if the key specification fed to the secret key factory is invalid
	 * @throws NoSuchAlgorithmException - Throws if the algorithm to be used for hashing does not exist
	 */
	public static byte[] getEncryptedPassword(String password, byte[] salt)
	throws NoSuchAlgorithmException, InvalidKeySpecException {
	  
		String algorithm = "PBKDF2WithHmacSHA1"; // PBKDF2 with SHA-1 as the hashing algorithm
		int derivedKeyLength = 160; // SHA-1 generates 160 bit hashes, so that's what makes sense here
		int iterations = 5000; // How many times the hash get repeated
		
		// Tells key factory what type of key to make
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
		
		// Instantiate factory with the algorithm to be used
		SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		
		// Return hashed password
		return f.generateSecret(spec).getEncoded();
	}
 
	
	/** 
	 * Generates a randomized byte array of length 8
	 * @return byte[] - Randomized byte array of length 8
	 * @throws NoSuchAlgorithmException - Throws if the algorithm to be used for hashing does not exist
	 */
	public static byte[] generateSalt() throws NoSuchAlgorithmException {
		// Apparently important to use SecureRandom instead of just Random
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
 
		// Generate a 8 byte (64 bit) salt
		byte[] salt = new byte[8];
		random.nextBytes(salt);
 
		return salt;
	 }
}