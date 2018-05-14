package pki;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.github.javafaker.Faker;

/**
 * This is class is responsible for providing 
 * the public keys of the various participants
 * 
 * Participants are identified and 
 * looked up their unique user ids 
 * (UUIDs). 
 * 
 * @author henryaspegren
 *
 */
public class PKIDirectory {
	
	private final Map<UUID, Account> lookupTable;
	
	public PKIDirectory(String dir) {
		this.lookupTable = new HashMap<>();
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles();
		for(File f : listOfFiles) {
			if(f.isFile()) {
				Account a = Account.loafFromFile(f);
				if(a != null) {
					this.lookupTable.put(a.getId(), a);
				}
			}
		}
	}
	
	public PKIDirectory(List<Account> accounts) {
		this.lookupTable = new HashMap<>();
		for(Account a : accounts ) {
			this.lookupTable.put(a.getId(), a);
		}
	}
	
	public Account getAccount(UUID id) {
		return this.lookupTable.get(id);
	}
	
	public Account getAccount(String uuidString) {
		UUID uuid = UUID.fromString(uuidString);
		return this.getAccount(uuid);
	}
	
	public Set<Account> getAllAccounts(){
		Set<Account> res = new HashSet<>();
		for(Account a : this.lookupTable.values()) {
			res.add(a);
		}
		return res;
	}
	
	public Set<UUID> getAllAccountIDs(){
		return this.lookupTable.keySet();
	}
	
	public static List<Account> generateRandomAccounts(int numberOfAccounts) {
		List<Account> accounts = new ArrayList<>();
		for(int i = 0; i < numberOfAccounts; i++) {
			Faker faker = new Faker();
			String firstName = faker.name().firstName(); 
			String lastName = faker.name().lastName(); 
			Account account = new Account(firstName, lastName);
			accounts.add(account);
			System.out.println("generating account "+(i+1)+
					" - of - "+numberOfAccounts+"("+faker.name().fullName()+")");
		}
		return accounts;
	}
	
}
