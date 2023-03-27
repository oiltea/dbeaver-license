package com.github.oiltea;

import org.jkiss.lm.*;
import org.jkiss.utils.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * LMMain
 *
 * @author oiltea@qq.com
 */
public class LMMain {

	private static final Map<String, LMProduct> products = new HashMap<>();

	static {
		products.put("LITE", new LMProduct("dbeaver-lite", "DB", "DBeaver Lite", "DBeaver Lite Edition", "23.0", LMProductType.DESKTOP, new Date(), new String[0]));
		products.put("EE", new LMProduct("dbeaver-ee", "DB", "DBeaver Enterprise", "DBeaver Enterprise Edition", "23.0", LMProductType.DESKTOP, new Date(), new String[0]));
		products.put("UE", new LMProduct("dbeaver-ue", "DB", "DBeaver Ultimate", "DBeaver Ultimate Edition", "23.0", LMProductType.DESKTOP, new Date(), new String[0]));
	}

	public static void main(String[] args) throws Throwable {
		System.out.println("LM 2.0");

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("License type (UE):");
		String licenseType = in.readLine();
		if (licenseType.isEmpty()) {
			licenseType = "UE";
		}

		LMProduct product = products.get(licenseType);
		if (Objects.isNull(product)) {
			System.err.println("License type '" + licenseType + "' does not exist,Please enter 'LITE' or 'EE' or 'UE'.");
			return;
		}

		String licenseID = LMUtils.generateLicenseId(product);
		System.out.println("License ID: " + licenseID);
		System.out.println("Product ID (" + product.getId() + "):");
		String productID = in.readLine();
		if (productID.isEmpty()) {
			productID = product.getId();
		}

		System.out.println("Product version (" + product.getVersion() + "):");
		String productVersion = in.readLine();
		if (productVersion.isEmpty()) {
			productVersion = product.getVersion();
		}

		System.out.println("Owner ID (1):");
		String ownerID = in.readLine();
		if (ownerID.isEmpty()) {
			ownerID = "1";
		}

		System.out.println("Owner company (JKISS):");
		String ownerCompany = in.readLine();
		if (ownerCompany.isEmpty()) {
			ownerCompany = "JKISS";
		}

		System.out.println("Owner name:");
		String ownerName = in.readLine();
		System.out.println("Owner email:");
		String ownerEmail = in.readLine();

		KeyPair keyPair = LMEncryption.generateKeyPair(2048);
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		LMLicense license = new LMLicense(licenseID, LMLicenseType.ULTIMATE, new Date(), new Date(), null, LMLicense.FLAG_UNLIMITED_SERVERS, productID, productVersion, ownerID, ownerCompany, ownerName, ownerEmail);
		byte[] licenseData = license.getData();
		byte[] licenseEncrypted = LMEncryption.encrypt(licenseData, privateKey);

		System.out.println();
		System.out.println("--- PUBLIC KEY ---");
		System.out.println(Base64.splitLines(Base64.encode(publicKey.getEncoded()), 76));
		System.out.println("--- PRIVATE KEY ---");
		System.out.println(Base64.splitLines(Base64.encode(privateKey.getEncoded()), 76));
		System.out.println("--- LICENSE ---");
		System.out.println(Base64.splitLines(Base64.encode(licenseEncrypted), 76));
	}
}
