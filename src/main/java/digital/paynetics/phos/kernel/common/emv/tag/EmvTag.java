package digital.paynetics.phos.kernel.common.emv.tag;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import digital.paynetics.phos.kernel.common.misc.ByteUtils;


/**
 * http://www.emvlab.org/emvtags/all/
 * <p>
 * <p>
 * The coding of primitive context-specific class data objects in the ranges '80' to '9E' and '9F00' to '9F4F' is reserved for EMV
 * specification. The coding of primitive context-specific class data objects in the range '9F50' to '9F7F' is reserved for the
 * payment systems.
 */
@SuppressWarnings({"unused", "SpellCheckingInspection"})
public enum EmvTag {
    UNKNOWN("FFFFFF", TagValueTypeEnum.BINARY, "Unknown tag", ""),
    // @formatter:off
    // One byte tags
    // 7816-4 Inter-industry data object for tag allocation authority
    UNIVERSAL_TAG_FOR_OID("06", TagValueTypeEnum.BINARY, "Object Identifier (OID)", "Universal tag for OID"),
    COUNTRY_CODE("41", TagValueTypeEnum.NUMERIC, "Country Code", "Country code (encoding specified in ISO 3166-1) and optional national data"),
    ISSUER_IDENTIFICATION_NUMBER("42", TagValueTypeEnum.NUMERIC, "Issuer Identification Number (IIN)", "The number that identifies the major industry and the card issuer and that forms the first part of the Primary Account Number (PAN)"),
    // 7816-4 Inter-industry data objects for application identification and selection
    ADF_NAME("4f", TagValueTypeEnum.BINARY, "Application Identifier (AID) - card", "Identifies the application as described in ISO/IEC 7816-5"),
    APPLICATION_LABEL("50", TagValueTypeEnum.TEXT, "Application Label", "Mnemonic associated with the AID according to ISO/IEC 7816-5"),
    PATH("51", TagValueTypeEnum.BINARY, "File reference data element", "ISO-7816 Path"),
    COMMAND_APDU("52", TagValueTypeEnum.BINARY, "Command APDU", ""),
    DISCRETIONARY_DATA_OR_TEMPLATE("53", TagValueTypeEnum.BINARY, "Discretionary data (or template)", ""),
    APPLICATION_TEMPLATE("61", TagValueTypeEnum.BINARY, "Application Template", "Contains one or more data objects relevant to an application directory entry according to ISO/IEC 7816-5"),
    FCI_TEMPLATE("6f", TagValueTypeEnum.BINARY, "File Control Information (FCI) Template", "Set of file control parameters and file management data (according to ISO/IEC 7816-4)"),
    //    DD_TEMPLATE("73", TagValueTypeEnum.BINARY, "Directory Discretionary Template", "Issuer discretionary part of the directory according to ISO/IEC 7816-5"),
    READ_RECORD_RESPONSE_MESSAGE_TEMPLATE("73", TagValueTypeEnum.BINARY, "READ RECORD Response Message Template", ""),
    DEDICATED_FILE_NAME("84", TagValueTypeEnum.BINARY, "Dedicated File (DF) Name", "Identifies the name of the DF as described in ISO/IEC 7816-4"),
    SFI("88", TagValueTypeEnum.BINARY, "Short File Identifier (SFI)", "Identifies the SFI to be used in the commands related to a given AEF or DDF. The SFI data object is a binary field with the three high order bits set to zero"),
    FCI_PROPRIETARY_TEMPLATE("a5", TagValueTypeEnum.BINARY, "File Control Information (FCI) Proprietary Template", "Identifies the data object proprietary to this specification in the FCI template according to ISO/IEC 7816-4"),
    ISSUER_URL("5f50", TagValueTypeEnum.TEXT, "Issuer URL", "The URL provides the location of the Issuerâ€™s Library Server on the Internet"),
    // EMV
    TRACK_2_EQV_DATA("57", TagValueTypeEnum.BINARY, "Track 2 Equivalent Data", "Contains the data elements of track 2 according to ISO/IEC 7813, excluding start sentinel, end sentinel, and Longitudinal Redundancy Check (LRC)"),
    PAN("5a", TagValueTypeEnum.NUMERIC, "Application Primary Account Number (PAN)", "Valid cardholder account number"),
    RECORD_TEMPLATE("70", TagValueTypeEnum.BINARY, "Record Template (EMV Proprietary)", "Template proprietary to the EMV specification"),
    ISSUER_SCRIPT_TEMPLATE_1("71", TagValueTypeEnum.BINARY, "Issuer Script Template 1", "Contains proprietary issuer data for transmission to the ICC before the second GENERATE AC command"),
    ISSUER_SCRIPT_TEMPLATE_2("72", TagValueTypeEnum.BINARY, "Issuer Script Template 2", "Contains proprietary issuer data for transmission to the ICC after the second GENERATE AC command"),
    RESPONSE_MESSAGE_TEMPLATE_2("77", TagValueTypeEnum.BINARY, "Response Message Template Format 2", "Contains the data objects (with tags and lengths) returned by the ICC in response to a command"),
    RESPONSE_MESSAGE_TEMPLATE_1("80", TagValueTypeEnum.BINARY, "Response Message Template Format 1", "Contains the data objects (without tags and lengths) returned by the ICC in response to a command"),
    AMOUNT_AUTHORISED_BINARY("81", TagValueTypeEnum.BINARY, "Amount, Authorised (Binary)", "Authorised amount of the transaction (excluding adjustments)"),
    APPLICATION_INTERCHANGE_PROFILE("82", TagValueTypeEnum.BINARY, "Application Interchange Profile", "Indicates the capabilities of the card to support specific functions in the application"),
    COMMAND_TEMPLATE("83", TagValueTypeEnum.BINARY, "Command Template", "Identifies the data field of a command message"),
    ISSUER_SCRIPT_COMMAND("86", TagValueTypeEnum.BINARY, "Issuer Script Command", "Contains a command for transmission to the ICC"),
    APPLICATION_PRIORITY_INDICATOR("87", TagValueTypeEnum.BINARY, "Application Priority Indicator", "Indicates the priority of a given application or group of applications in a directory"),
    AUTHORISATION_CODE("89", TagValueTypeEnum.BINARY, "Authorisation Code", "Value generated by the authorisation authority for an approved transaction"),
    AUTHORISATION_RESPONSE_CODE("8a", TagValueTypeEnum.TEXT, "Authorisation Response Code", "Code that defines the disposition of a message"),
    CDOL1("8c", TagValueTypeEnum.DOL, "Card Risk Management Data Object List 1 (CDOL1)", "List of data objects (tag and length) to be passed to the ICC in the first GENERATE AC command"),
    CDOL2("8d", TagValueTypeEnum.DOL, "Card Risk Management Data Object List 2 (CDOL2)", "List of data objects (tag and length) to be passed to the ICC in the second GENERATE AC command"),
    CVM_LIST("8e", TagValueTypeEnum.BINARY, "Cardholder Verification Method (CVM) List", "Identifies a method of verification of the cardholder supported by the application"),
    CA_PUBLIC_KEY_INDEX_CARD("8f", TagValueTypeEnum.BINARY, "Certification Authority Public Key Index - card", "Identifies the certification authority's public key in conjunction with the RID"),
    ISSUER_PUBLIC_KEY_CERT("90", TagValueTypeEnum.BINARY, "Issuer Public Key Certificate", "Issuer public key certified by a certification authority"),
    ISSUER_AUTHENTICATION_DATA("91", TagValueTypeEnum.BINARY, "Issuer Authentication Data", "Data sent to the ICC for online issuer authentication"),
    ISSUER_PUBLIC_KEY_REMAINDER("92", TagValueTypeEnum.BINARY, "Issuer Public Key Remainder", "Remaining digits of the Issuer Public Key Modulus"),
    SIGNED_STATIC_APP_DATA("93", TagValueTypeEnum.BINARY, "Signed Static Application Data", "Digital signature on critical application parameters for SDA"),
    APPLICATION_FILE_LOCATOR("94", TagValueTypeEnum.BINARY, "Application File Locator (AFL)", "Indicates the location (SFI, range of records) of the AEFs related to a given application"),
    TERMINAL_VERIFICATION_RESULTS("95", TagValueTypeEnum.BINARY, "Terminal Verification Results (TVR)/Third Party Data", "Status of the different functions as seen from the terminal"),
    TDOL("97", TagValueTypeEnum.BINARY, "Transaction Certificate Data Object List (TDOL)", "List of data objects (tag and length) to be used by the terminal in generating the TC Hash Value"),
    TC_HASH_VALUE("98", TagValueTypeEnum.BINARY, "Transaction Certificate (TC) Hash Value", "Result of a hash function specified in Book 2, Annex B3.1"),
    TRANSACTION_PIN_DATA("99", TagValueTypeEnum.BINARY, "Transaction Personal Identification Number (PIN) Data", "Data entered by the cardholder for the purpose of the PIN verification"),
    TRANSACTION_DATE("9a", TagValueTypeEnum.NUMERIC, "Transaction Date", "Local date that the transaction was authorised"),
    TRANSACTION_STATUS_INFORMATION("9b", TagValueTypeEnum.BINARY, "Transaction Status Information", "Indicates the functions performed in a transaction"),
    TRANSACTION_TYPE("9c", TagValueTypeEnum.NUMERIC, "Transaction Type", "Indicates the type of financial transaction, represented by the first two digits of ISO 8583:1987 Processing Code"),
    DDF_NAME("9d", TagValueTypeEnum.BINARY, "Directory Definition File (DDF) Name", "Identifies the name of a DF associated with a directory"),

    MASTERCARD_PROPRIETARY_TEST_TAG_C1("c1", TagValueTypeEnum.BINARY, "Mastercard test tag C1", ""),
    MASTERCARD_PROPRIETARY_TEST_TAG_C2("c2", TagValueTypeEnum.BINARY, "Mastercard test tag C2", ""),
    MASTERCARD_PROPRIETARY_TEST_TAG_C3("c3", TagValueTypeEnum.BINARY, "Mastercard test tag C3", ""),
    MASTERCARD_PROPRIETARY_TEST_TAG_C4("c4", TagValueTypeEnum.BINARY, "Mastercard test tag C4", ""),
    MASTERCARD_PROPRIETARY_TEST_TAG_C5("c5", TagValueTypeEnum.BINARY, "Mastercard test tag C5", ""),
    MASTERCARD_PROPRIETARY_TEST_TAG_C6("c6", TagValueTypeEnum.BINARY, "Mastercard test tag C6", ""),
    MASTERCARD_PROPRIETARY_TEST_TAG_C7("c7", TagValueTypeEnum.BINARY, "Mastercard test tag C7", ""),
    //    MASTERCARD_PROPRIETARY_TEST_TAG("d7", TagValueTypeEnum.BINARY, "Mastercard proprietary", ""),
    // Two byte tags
    CARDHOLDER_NAME("5f20", TagValueTypeEnum.TEXT, "Cardholder Name", "Indicates cardholder name according to ISO 7813"),
    MASTERARD_TEST_TAG_5F21("5F21", TagValueTypeEnum.BINARY, "Mastercard test tag", ""),
    APP_EXPIRATION_DATE("5f24", TagValueTypeEnum.NUMERIC, "Application Expiration Date", "Date after which application expires"),
    APP_EFFECTIVE_DATE("5f25", TagValueTypeEnum.NUMERIC, "Application Effective Date", "Date from which the application may be used"),
    ISSUER_COUNTRY_CODE("5f28", TagValueTypeEnum.NUMERIC, "Issuer Country Code", "Indicates the country of the issuer according to ISO 3166"),
    TRANSACTION_CURRENCY_CODE("5f2a", TagValueTypeEnum.NUMERIC, "Transaction Currency Code", "Indicates the currency code of the transaction according to ISO 4217"),
    LANGUAGE_PREFERENCE("5f2d", TagValueTypeEnum.TEXT, "Language Preference", "1â€“4 languages stored in order of preference, each represented by 2 alphabetical characters according to ISO 639"),
    SERVICE_CODE("5f30", TagValueTypeEnum.NUMERIC, "Service Code", "Service code as defined in ISO/IEC 7813 for track 1 and track 2"),
    PAN_SEQUENCE_NUMBER("5f34", TagValueTypeEnum.NUMERIC, "Application Primary Account Number (PAN) Sequence Number", "Identifies and differentiates cards with the same PAN"),
    TRANSACTION_CURRENCY_EXP("5f36", TagValueTypeEnum.NUMERIC, "Transaction Currency Exponent", "Indicates the implied position of the decimal point from the right of the transaction amount represented according to ISO 4217"),
    IBAN("5f53", TagValueTypeEnum.BINARY, "International Bank Account Number (IBAN)", "Uniquely identifies the account of a customer at a financial institution as defined in ISO 13616"),
    BANK_IDENTIFIER_CODE("5f54", TagValueTypeEnum.MIXED, "Bank Identifier Code (BIC)", "Uniquely identifies a bank as defined in ISO 9362"),
    ISSUER_COUNTRY_CODE_ALPHA2("5f55", TagValueTypeEnum.TEXT, "Issuer Country Code (alpha2 format)", "Indicates the country of the issuer as defined in ISO 3166 (using a 2 character alphabetic code)"),
    ISSUER_COUNTRY_CODE_ALPHA3("5f56", TagValueTypeEnum.TEXT, "Issuer Country Code (alpha3 format)", "Indicates the country of the issuer as defined in ISO 3166 (using a 3 character alphabetic code)"),
    ACCOUNT_TYPE("5F57", TagValueTypeEnum.NUMERIC, "Account Type", "Indicates the type of account selected on the terminal"),
    ACQUIRER_IDENTIFIER("9f01", TagValueTypeEnum.NUMERIC, "Acquirer Identifier", "Uniquely identifies the acquirer within each payment system"),
    AMOUNT_AUTHORISED_NUMERIC("9f02", TagValueTypeEnum.NUMERIC, "Amount, Authorised (Numeric)", "Authorised amount of the transaction (excluding adjustments)"),
    AMOUNT_OTHER_NUMERIC("9f03", TagValueTypeEnum.NUMERIC, "Amount, Other (Numeric)", "Secondary amount associated with the transaction representing a cashback amount"),
    AMOUNT_OTHER_BINARY("9f04", TagValueTypeEnum.NUMERIC, "Amount, Other (Binary)", "Secondary amount associated with the transaction representing a cashback amount"),
    APP_DISCRETIONARY_DATA("9f05", TagValueTypeEnum.BINARY, "Application Discretionary Data", "Issuer or payment system specified data relating to the application"),
    AID_TERMINAL("9f06", TagValueTypeEnum.BINARY, "Application Identifier (AID) - terminal", "Identifies the application as described in ISO/IEC 7816-5"),
    APP_USAGE_CONTROL("9f07", TagValueTypeEnum.BINARY, "Application Usage Control", "Indicates issuerâ€™s specified restrictions on the geographic usage and services allowed for the application"),
    APP_VERSION_NUMBER_CARD("9f08", TagValueTypeEnum.BINARY, "Application Version Number - card", "Version number assigned by the payment system for the application"),
    APP_SELECTION_REGISTERED_PROPRIETARY_DATA("9f0a", TagValueTypeEnum.BINARY, "Application Selection Registered Proprietary Data", "Intended to be used during Application Selection to identify the card type"),
    APP_VERSION_NUMBER_TERMINAL("9f09", TagValueTypeEnum.BINARY, "Application Version Number - terminal", "Version number assigned by the payment system for the application"),
    CARDHOLDER_NAME_EXTENDED("9f0b", TagValueTypeEnum.TEXT, "Cardholder Name Extended", "Indicates the whole cardholder name when greater than 26 characters using the same coding convention as in ISO 7813"),
    ISSUER_ACTION_CODE_DEFAULT("9f0d", TagValueTypeEnum.BINARY, "Issuer Action Code - Default", "Specifies the issuerâ€™s conditions that cause a transaction to be rejected if it might have been approved online, but the terminal is unable to process the transaction online"),
    ISSUER_ACTION_CODE_DENIAL("9f0e", TagValueTypeEnum.BINARY, "Issuer Action Code - Denial", "Specifies the issuerâ€™s conditions that cause the denial of a transaction without attempt to go online"),
    ISSUER_ACTION_CODE_ONLINE("9f0f", TagValueTypeEnum.BINARY, "Issuer Action Code - Online", "Specifies the issuerâ€™s conditions that cause a transaction to be transmitted online"),
    ISSUER_APPLICATION_DATA("9f10", TagValueTypeEnum.BINARY, "Issuer Application Data", "Contains proprietary application data for transmission to the issuer in an online transaction"),
    ISSUER_CODE_TABLE_INDEX("9f11", TagValueTypeEnum.NUMERIC, "Issuer Code Table Index", "Indicates the code table according to ISO/IEC 8859 for displaying the Application Preferred Name"),
    APP_PREFERRED_NAME("9f12", TagValueTypeEnum.TEXT, "Application Preferred Name", "Preferred mnemonic associated with the AID"),
    LAST_ONLINE_ATC_REGISTER("9f13", TagValueTypeEnum.BINARY, "Last Online Application Transaction Counter (ATC) Register", "ATC value of the last transaction that went online"),
    LOWER_CONSEC_OFFLINE_LIMIT("9f14", TagValueTypeEnum.BINARY, "Lower Consecutive Offline Limit", "Issuer-specified preference for the maximum number of consecutive offline transactions for this ICC application allowed in a terminal with online capability"),
    MERCHANT_CATEGORY_CODE("9f15", TagValueTypeEnum.NUMERIC, "Merchant Category Code", "Classifies the type of business being done by the merchant, represented according to ISO 8583:1993 for Card Acceptor Business Code"),
    MERCHANT_IDENTIFIER("9f16", TagValueTypeEnum.TEXT, "Merchant Identifier", "When concatenated with the Acquirer Identifier, uniquely identifies a given merchant"),
    PIN_TRY_COUNTER("9f17", TagValueTypeEnum.BINARY, "Personal Identification Number (PIN) Try Counter", "Number of PIN tries remaining"),
    ISSUER_SCRIPT_IDENTIFIER("9f18", TagValueTypeEnum.BINARY, "Issuer Script Identifier", "Identification of the Issuer Script"),
    TERMINAL_COUNTRY_CODE("9f1a", TagValueTypeEnum.NUMERIC, "Terminal Country Code", "Indicates the country of the terminal, represented according to ISO 3166"),
    TERMINAL_FLOOR_LIMIT("9f1b", TagValueTypeEnum.BINARY, "Terminal Floor Limit", "Indicates the floor limit in the terminal in conjunction with the AID"),
    TERMINAL_IDENTIFICATION("9f1c", TagValueTypeEnum.TEXT, "Terminal Identification", "Designates the unique location of a terminal at a merchant"),
    TERMINAL_RISK_MANAGEMENT_DATA("9f1d", TagValueTypeEnum.BINARY, "Terminal Risk Management Data", "Application-specific value used by the card for risk management purposes"),
    INTERFACE_DEVICE_SERIAL_NUMBER("9f1e", TagValueTypeEnum.TEXT, "Interface Device (IFD) Serial Number", "Unique and permanent serial number assigned to the IFD by the manufacturer"),
    TRACK1_DISCRETIONARY_DATA("9f1f", TagValueTypeEnum.TEXT, "[Magnetic Stripe] Track 1 Discretionary Data", "Discretionary part of track 1 according to ISO/IEC 7813"),
    TRACK2_DISCRETIONARY_DATA("9f20", TagValueTypeEnum.TEXT, "[Magnetic Stripe] Track 2 Discretionary Data", "Discretionary part of track 2 according to ISO/IEC 7813"),
    TRANSACTION_TIME("9f21", TagValueTypeEnum.NUMERIC, "Transaction Time (HHMMSS)", "Local time that the transaction was authorised"),
    CA_PUBLIC_KEY_INDEX_TERMINAL("9f22", TagValueTypeEnum.BINARY, "Certification Authority Public Key Index - Terminal", "Identifies the certification authority's public key in conjunction with the RID"),
    UPPER_CONSEC_OFFLINE_LIMIT("9f23", TagValueTypeEnum.BINARY, "Upper Consecutive Offline Limit", "Issuer-specified preference for the maximum number of consecutive offline transactions for this ICC application allowed in a terminal without online capability"),
    PAYMENT_ACCOUNT_REFFERENCE("9f24", TagValueTypeEnum.TEXT, "Payment Account Reference (PAR)", "Uniquely identifies the underlying cardholder account to which a payment token is associated, as defined in [EMV Tokenisation]"),
    APP_CRYPTOGRAM("9f26", TagValueTypeEnum.BINARY, "Application Cryptogram", "Cryptogram returned by the ICC in response of the GENERATE AC command"),
    CRYPTOGRAM_INFORMATION_DATA("9f27", TagValueTypeEnum.BINARY, "Cryptogram Information Data", "Indicates the type of cryptogram and the actions to be performed by the terminal"),
    ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_CERT("9f2d", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Certificate", "ICC PIN Encipherment Public Key certified by the issuer"),
    ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_EXP("9f2e", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Exponent", "ICC PIN Encipherment Public Key Exponent used for PIN encipherment"),
    ICC_PIN_ENCIPHERMENT_PUBLIC_KEY_REM("9f2f", TagValueTypeEnum.BINARY, "ICC PIN Encipherment Public Key Remainder", "Remaining digits of the ICC PIN Encipherment Public Key Modulus"),
    ISSUER_PUBLIC_KEY_EXPONENT("9f32", TagValueTypeEnum.BINARY, "Issuer Public Key Exponent", "Issuer public key exponent used for the verification of the Signed Static Application Data and the ICC Public Key Certificate"),
    TERMINAL_CAPABILITIES("9f33", TagValueTypeEnum.BINARY, "Terminal Capabilities", "Indicates the card data input, CVM, and security capabilities of the terminal"),
    CVM_RESULTS("9f34", TagValueTypeEnum.BINARY, "Cardholder Verification (CVM) Results", "Indicates the results of the last CVM performed"),
    TERMINAL_TYPE("9f35", TagValueTypeEnum.NUMERIC, "Terminal Type", "Indicates the environment of the terminal, its communications capability, and its operational control"),
    APP_TRANSACTION_COUNTER("9f36", TagValueTypeEnum.BINARY, "Application Transaction Counter (ATC)", "Counter maintained by the application in the ICC (incrementing the ATC is managed by the ICC)"),
    UNPREDICTABLE_NUMBER("9f37", TagValueTypeEnum.BINARY, "Unpredictable Number", "Value to provide variability and uniqueness to the generation of a cryptogram"),
    PDOL("9f38", TagValueTypeEnum.DOL, "Processing Options Data Object List (PDOL)", "Contains a list of terminal resident data objects (tags and lengths) needed by the ICC in processing the GET PROCESSING OPTIONS command"),
    POINT_OF_SERVICE_ENTRY_MODE("9f39", TagValueTypeEnum.NUMERIC, "Point-of-Service (POS) Entry Mode", "Indicates the method by which the PAN was entered, according to the first two digits of the ISO 8583:1987 POS Entry Mode"),
    AMOUNT_REFERENCE_CURRENCY("9f3a", TagValueTypeEnum.BINARY, "Amount, Reference Currency", "Authorised amount expressed in the reference currency"),
    APP_REFERENCE_CURRENCY("9f3b", TagValueTypeEnum.NUMERIC, "Application Reference Currency", "1â€“4 currency codes used between the terminal and the ICC when the Transaction Currency Code is different from the Application Currency Code; each code is 3 digits according to ISO 4217"),
    TRANSACTION_REFERENCE_CURRENCY_CODE("9f3c", TagValueTypeEnum.NUMERIC, "Transaction Reference Currency Code", "Code defining the common currency used by the terminal in case the Transaction Currency Code is different from the Application Currency Code"),
    TRANSACTION_REFERENCE_CURRENCY_EXP("9f3d", TagValueTypeEnum.NUMERIC, "Transaction Reference Currency Exponent", "Indicates the implied position of the decimal point from the right of the transaction amount, with the Transaction Reference Currency Code represented according to ISO 4217"),
    ADDITIONAL_TERMINAL_CAPABILITIES("9f40", TagValueTypeEnum.BINARY, "Additional Terminal Capabilities", "Indicates the data input and output capabilities of the terminal"),
    TRANSACTION_SEQUENCE_COUNTER("9f41", TagValueTypeEnum.NUMERIC, "Transaction Sequence Counter", "Counter maintained by the terminal that is incremented by one for each transaction"),
    APPLICATION_CURRENCY_CODE("9f42", TagValueTypeEnum.NUMERIC, "Application Currency Code", "Indicates the currency in which the account is managed according to ISO 4217"),
    APP_REFERENCE_CURRECY_EXPONENT("9f43", TagValueTypeEnum.NUMERIC, "Application Reference Currency Exponent", "Indicates the implied position of the decimal point from the right of the amount, for each of the 1â€“4 reference currencies represented according to ISO 4217"),
    APP_CURRENCY_EXPONENT("9f44", TagValueTypeEnum.NUMERIC, "Application Currency Exponent", "Indicates the implied position of the decimal point from the right of the amount represented according to ISO 4217"),
    DATA_AUTHENTICATION_CODE("9f45", TagValueTypeEnum.BINARY, "Data Authentication Code", "An issuer assigned value that is retained by the terminal during the verification process of the Signed Static Application Data"),
    ICC_PUBLIC_KEY_CERT("9f46", TagValueTypeEnum.BINARY, "ICC Public Key Certificate", "ICC Public Key certified by the issuer"),
    ICC_PUBLIC_KEY_EXPONENT("9f47", TagValueTypeEnum.BINARY, "ICC Public Key Exponent", "ICC Public Key Exponent used for the verification of the Signed Dynamic Application Data"),
    ICC_PUBLIC_KEY_REMAINDER("9f48", TagValueTypeEnum.BINARY, "ICC Public Key Remainder", "Remaining digits of the ICC Public Key Modulus"),
    DDOL("9f49", TagValueTypeEnum.DOL, "Dynamic Data Authentication Data Object List (DDOL)", "List of data objects (tag and length) to be passed to the ICC in the INTERNAL AUTHENTICATE command"),
    STATIC_DATA_AUTHENTICATION_TAG_LIST("9f4a", TagValueTypeEnum.BINARY, "Static Data Authentication Tag List", "List of tags of primitive data objects defined in this specification whose value fields are to be included in the Signed Static or Dynamic Application Data"),
    SIGNED_DYNAMIC_APPLICATION_DATA("9f4b", TagValueTypeEnum.BINARY, "Signed Dynamic Application Data", "Digital signature on critical application parameters for DDA or CDA"),
    ICC_DYNAMIC_NUMBER("9f4c", TagValueTypeEnum.BINARY, "ICC Dynamic Number", "Time-variant number generated by the ICC, to be captured by the terminal"),
    LOG_ENTRY("9f4d", TagValueTypeEnum.BINARY, "Log Entry", "Provides the SFI of the Transaction Log file and its number of records"),
    MERCHANT_NAME_AND_LOCATION("9f4e", TagValueTypeEnum.TEXT, "Merchant Name and Location", "Indicates the name and location of the merchant"),
    LOG_FORMAT("9f4f", TagValueTypeEnum.DOL, "Log Format", "List (in tag and length format) of data objects representing the logged data elements that are passed to the terminal when a transaction log record is read"),
    FCI_ISSUER_DISCRETIONARY_DATA("bf0c", TagValueTypeEnum.BINARY, "File Control Information (FCI) Issuer Discretionary Data", "Issuer discretionary part of the FCI (e.g. O/S Manufacturer proprietary data)"),
    VISA_LOG_ENTRY__MS_DS_INPUT("df60", TagValueTypeEnum.BINARY, "VISA Log Entry", ""),
    // The Track 1 Data may be present in the file read using the READ
    // RECORD command during a mag-stripe mode transaction. It is made up of
    // the following sub-fields:
    // +------------------------+--------------+--------------------+
    // | Data Field | Length | Format |
    // +------------------------+--------------+--------------------+
    // | Format Code | 1 | '42' |
    // | Primary Account Number | var up to 19 | digits |
    // | Field Separator | 1 | '5E' |
    // | Name | 2-26 | (see ISO/IEC 7813) |
    // | Field Separator | 1 | '5E' |
    // | Expiry Date | 4 | YYMM |
    // | Service Code | 3 | digits |
    // | Discretionary Data | var. | ans |
    // +------------------------+--------------+--------------------+
    // BER-TLV[56, 29 (raw 29), 42 xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx xx 5e 20 2f 5e xx xx xx xx 32 30 31 30 31 30 30 30
    // 30 30 30 30 30 30 30 30]
    // BER-TLV[56, 34 (raw 34), 42 XX XX XX XX XX XX XX XX XX XX XX XX XX XX XX XX 5e 20 2f 5e YY YY MM MM 32 30 31 30 30 30 30 30
    // 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30
    TRACK1_DATA("56", TagValueTypeEnum.TEXT, "Track 1 Data", "Track 1 Data contains the data objects of the track 1 according to [ISO/IEC 7813] Structure B, excluding start sentinel, end sentinel and LRC."),
    // '9F50' to '9F7F' are reserved for the payment systems (proprietary)

    // The following tags are specified in EMV Contactless (Book A)
    TERMINAL_TRANSACTION_QUALIFIERS__PUNATC_TRACK2("9f66", TagValueTypeEnum.BINARY, "Terminal Transaction Qualifiers", "Provided by the reader in the GPO command and used by the card to determine processing choices based on reader functionality"),
    // 9f6b 13 BB BB BB BB BB BB BB BB dY YM M2 01 00 00 00 00 00 00 0f
    TRACK2_DATA("9f6b", TagValueTypeEnum.BINARY, "Track 2 Data", "Track 2 Data contains the data objects of the track 2 according to [ISO/IEC 7813] Structure B, excluding start sentinel, end sentinel and LRC."),
    // The Track 2 Data is present in the file read using the READ RECORD command
    // during a mag-stripe mode transaction. It is made up of the following
    // sub-fields (same as tag 57):
    //
    // +------------------------+-----------------------+-----------+
    // | Data Field | Length | Format |
    // +------------------------+-----------------------+-----------+
    // | Primary Account Number | var. up to 19 nibbles | n |
    // | Field Separator | 1 nibble | b ('D') |
    // | Expiry Date | 2 | n (YYMM) |
    // | Service Code | 3 nibbles | n |
    // | Discretionary Data | var. | n |
    // | Padding if needed | 1 nibble | b ('F') |
    // +------------------------+-----------------------+-----------+
    VISA_FORM_FACTOR_INDICATOR__MS_THIRD_PARTY_DATA("9f6e", TagValueTypeEnum.BINARY, "Visa Form Factor Indicator (FFI)/Mastercard Third Party Data", ""),
    // These are specified in EMV Contactless (Book B)
    EXTENDED_SELECTION("9f29", TagValueTypeEnum.BINARY, "Indicates the card's preference for the kernel on which the contactless application can be processed", ""),
    KERNEL_IDENTIFIER("9f2a", TagValueTypeEnum.BINARY, "The value to be appended to the ADF Name in the data field of the SELECT command, if the Extended Selection Support flag is present and set to 1", ""),
    MASTERCARD_UPPER_OFFLINE_AMOUNT("9f52", TagValueTypeEnum.BINARY, "Upper Cumulative Domestic Offline Transaction Amount", "Issuer specified data element indicating the required maximum cumulative offline amount allowed for the application before the transaction goes online."),
    // MasterCard Tags
    // EMV Cap
    // 9f56 0c 0f00007fffffe00000000000
    // 9f56 1d 00007fffffe00000000000000000000000000000000000000000000000
    // 9f56 0b 00 00 7f ff ff 00 00 00 00 00 00
    TAG_9f56("9f56", TagValueTypeEnum.BINARY, "?", ""),

    // //EMV-CAP tags (see also VISA Tags)
    // //9f55 01 c0
    // //9f55 01 00
    // public static final Tag TAG_9f55 = new TagImpl("9f55", TagValueTypeEnum.BINARY, "?", ""),
    // Example: BER-TLV[9f6c, 02 (raw 02), 0001]
    VISA_CARD_TRANSACTION_QUALIFIERS("9f6c", TagValueTypeEnum.BINARY, "VISA Card Transaction Qualifiers", "Indicates to the device the card CVM requirements, issuer preferences, and card capabilities."),
    // Transaction log data
    // df3e 01 01
    TAG_df3e("df3e", TagValueTypeEnum.BINARY, "?", ""),
    // These are specified in EMV Contactless (Book C-2) "MasterCard"
    OFFLINE_ACCUMULATOR_BALANCE("9f50", TagValueTypeEnum.BINARY, "Offline Accumulator Balance", "Represents the amount of offline spending available in the Card."),

    DRDOL("9f51", TagValueTypeEnum.BINARY, "DRDOL", "A data object in the Card that provides the Kernel with a list of data objects that must be passed to the Card in the data field of the RECOVER AC command"),
    TRANSACTION_CATEGORY_CODE("9f53", TagValueTypeEnum.BINARY, "Transaction Category Code", ""),
    DS_ODS_CARD("9f54", TagValueTypeEnum.BINARY, "DS ODS Card", ""),
    DSDOL("9f5b", TagValueTypeEnum.BINARY, "DSDOL", ""),
    DS_REQUESTED_OPERATOR_ID("9f5c", TagValueTypeEnum.BINARY, "DS Requested Operator ID", ""),
    // 9f5d 01 01
    APPLICATION_CAPABILITIES_INFORMATION("9f5d", TagValueTypeEnum.BINARY, "VISA Available Offline Spending Amount/MC Application Capabilities Information", "Lists a number of card features beyond regular payment"),
    DS_ID("9f5e", TagValueTypeEnum.BINARY, "Data Storage Identifier", "Constructed as follows: Application PAN (without any 'F' padding) || Application PAN Sequence Number (+ zero padding)"),
    DS_SLOT_AVAILABILITY("9f5f", TagValueTypeEnum.BINARY, "DS Slot Availability", ""),
    CVC3_TRACK1("9f60", TagValueTypeEnum.BINARY, "CVC3 (Track1)", "The CVC3 (Track1) is a 2-byte cryptogram returned by the Card in the response to the COMPUTE CRYPTOGRAPHIC CHECKSUM command."),
    CVC3_TRACK2("9f61", TagValueTypeEnum.BINARY, "CVC3 (Track2)", "The CVC3 (Track2) is a 2-byte cryptogram returned by the Card in the response to the COMPUTE CRYPTOGRAPHIC CHECKSUM command."),
    // 9f62 06 00 00 00 00 00 0e
    // 9f62 06 00 00 00 03 80 00
    PCVC3_TRACK1("9f62", TagValueTypeEnum.BINARY, "Track 1 bit map for CVC3", "PCVC3(Track1) indicates to the Kernel the positions in the discretionary data field of the Track 1 Data where the CVC3 (Track1) digits must be copied"),
    // 9f63 06 00 00 00 00 07 f0
    // 9f63 06 00 00 00 00 07 8e
    PUNATC_TRACK1("9f63", TagValueTypeEnum.BINARY, "Track 1 bit map for UN and ATC", "PUNATC(Track1) indicates to the Kernel the positions in the discretionary data field of Track 1 Data where the Unpredictable Number (Numeric) digits and Application Transaction Counter digits have to be copied."),
    // 9f64 01 03
    // 9f64 01 04
    NATC_TRACK1("9f64", TagValueTypeEnum.BINARY, "Track 1 number of ATC digits", "The value of NATC(Track1) represents the number of digits of the Application Transaction Counter to be included in the discretionary data field of Track 1 Data"),
    // 9f65 02 000e
    // 9f65 02 0070
    PCVC3_TRACK2("9f65", TagValueTypeEnum.BINARY, "Track 2 bit map for CVC3", "PCVC3(Track2) indicates to the Kernel the positions in the discretionary data field of the Track 2 Data where the CVC3 (Track2) digits must be copied"),
    // 9f66 02 07f0
    // 9f66 02 1e0e
    NATC_TRACK2("9f67", TagValueTypeEnum.BINARY, "Track 2 number of ATC digits", "The value of NATC(Track2) represents the number of digits of the Application Transaction Counter to be included in the discretionary data field of Track 2 Data"),
    VISA_CARD_AUTHENTICATION_RELATED_DATA__MASTERCARD_UDOL("9f69", TagValueTypeEnum.BINARY, "VISA Card Authentication Related Data/Mastercard UDOL", ""),
    UNPREDICTABLE_NUMBER_NUMERIC("9f6a", TagValueTypeEnum.BINARY, "Unpredictable Number (Numeric)", ""),
    MAG_STRIPE_APP_VERSION_NUMBER_READER("9f6d", TagValueTypeEnum.BINARY, "Mag-stripe Application Version Number (Reader)", ""),
    DS_SLOT_MANAGEMENT_CONTROL("9f6f", TagValueTypeEnum.BINARY, "DS Slot Management Control", ""),
    PROTECTED_DATA_ENVELOPE_1("9f70", TagValueTypeEnum.BINARY, "Protected Data Envelope 1", ""),
    PROTECTED_DATA_ENVELOPE_2("9f71", TagValueTypeEnum.BINARY, "Protected Data Envelope 2", ""),
    PROTECTED_DATA_ENVELOPE_3("9f72", TagValueTypeEnum.BINARY, "Protected Data Envelope 3", ""),
    PROTECTED_DATA_ENVELOPE_4("9f73", TagValueTypeEnum.BINARY, "Protected Data Envelope 4", ""),
    PROTECTED_DATA_ENVELOPE_5("9f74", TagValueTypeEnum.BINARY, "Protected Data Envelope 5", ""),
    UNPROTECTED_DATA_ENVELOPE_1("9f75", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 1", ""),
    UNPROTECTED_DATA_ENVELOPE_2("9f76", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 2", ""),
    UNPROTECTED_DATA_ENVELOPE_3("9f77", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 3", ""),
    UNPROTECTED_DATA_ENVELOPE_4("9f78", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 4", ""),
    UNPROTECTED_DATA_ENVELOPE_5("9f79", TagValueTypeEnum.BINARY, "Unprotected Data Envelope 5", ""),
    MERCHANT_CUSTOM_DATA("9f7c", TagValueTypeEnum.BINARY, "Merchant Custom Data", ""),
    DS_SUMMARY_1("9f7d", TagValueTypeEnum.BINARY, "DS Summary 1", ""),
    MOBILE_SUPPORT_INDICATOR("9F7E", TagValueTypeEnum.BINARY, "Mobile Support Indicator", ""),
    DS_SUMMARY_3("DF8102", TagValueTypeEnum.BINARY, "DS Summary 3", ""),
    DS_UNPREDICTABLE_NUMBER("9f7f", TagValueTypeEnum.BINARY, "DS Unpredictable Number", ""),
    POS_CARDHOLDER_INTERACTION_INFORMATION("df4b", TagValueTypeEnum.BINARY, "POS Cardholder Interaction Information", ""),
    DS_DIGEST_H("df61", TagValueTypeEnum.BINARY, "DS Digest H", ""),
    DS_ODS_INFO("df62", TagValueTypeEnum.BINARY, "DS ODS Info", ""),
    DS_ODS_TERM("df63", TagValueTypeEnum.BINARY, "DS ODS Term", ""),
    DS_SUMMARY_2("DF8101", TagValueTypeEnum.BINARY, "DS Summary 2", ""),
    BALANCE_READ_BEFORE_GEN_AC("df8104", TagValueTypeEnum.BINARY, "Balance Read Before Gen AC", ""),
    BALANCE_READ_AFTER_GEN_AC("df8105", TagValueTypeEnum.BINARY, "Balance Read After Gen AC", ""),
    DATA_NEEDED("df8106", TagValueTypeEnum.BINARY, "Data Needed", ""),
    CDOL1_RELATED_DATA("df8107", TagValueTypeEnum.BINARY, "CDOL1 Related Data", ""),
    DS_AC_TYPE("df8108", TagValueTypeEnum.BINARY, "DS AC Type", ""),
    DS_INPUT_TERM("df8109", TagValueTypeEnum.BINARY, "DS Input (Term)", ""),
    DS_ODS_INFO_FOR_READER("df810a", TagValueTypeEnum.BINARY, "DS ODS Info For Reader", ""),
    DS_SUMMARY_STATUS("df810b", TagValueTypeEnum.BINARY, "DS Summary Status", ""),
    KERNEL_ID("df810c", TagValueTypeEnum.BINARY, "Kernel ID", ""),
    DSVN_TERM("df810d", TagValueTypeEnum.BINARY, "DSVN Term", ""),
    POST_GEN_AC_PUT_DATA_STATUS("df810e", TagValueTypeEnum.BINARY, "Post-Gen AC Put Data Status", ""),
    PRE_GEN_AC_PUT_DATA_STATUS("df810f", TagValueTypeEnum.BINARY, "Pre-Gen AC Put Data Status", ""),
    PROCEED_TO_FIRST_WRITE_FLAG("df8110", TagValueTypeEnum.BINARY, "Proceed To First Write Flag", ""),
    PDOL_RELATED_DATA("df8111", TagValueTypeEnum.BINARY, "PDOL Related Data", ""),
    TAGS_TO_READ("df8112", TagValueTypeEnum.BINARY, "Tags To Read", ""),
    DRDOL_RELATED_DATA("df8113", TagValueTypeEnum.BINARY, "DRDOL Related Data", ""),
    REFERENCE_CONTROL_PARAMETER("df8114", TagValueTypeEnum.BINARY, "Reference Control Parameter", ""),
    ERROR_INDICATION("df8115", TagValueTypeEnum.BINARY, "Error Indication", ""),
    USER_INTERFACE_REQUEST_DATA("df8116", TagValueTypeEnum.BINARY, "User Interface Request Data", ""),
    CARD_DATA_INPUT_CAPABILITY("df8117", TagValueTypeEnum.BINARY, "Card Data Input Capability", ""),
    CVM_CAPABILITY_CVM_REQUIRED("df8118", TagValueTypeEnum.BINARY, "CVM Capability - CVM Required", ""),
    CVM_CAPABILITY_NO_CVM_REQUIRED("df8119", TagValueTypeEnum.BINARY, "CVM Capability - No CVM Required", ""),
    DEFAULT_UDOL("df811a", TagValueTypeEnum.BINARY, "Default UDOL", ""),
    KERNEL_CONFIGURATION("df811b", TagValueTypeEnum.BINARY, "Kernel Configuration", ""),
    MAX_LIFETIME_TORN_TRANSACTION_LOG_REC("df811c", TagValueTypeEnum.BINARY, "Max Lifetime of Torn Transaction Log Record", ""),
    MAX_NUMBER_TORN_TRANSACTION_LOG_REC("df811d", TagValueTypeEnum.BINARY, "Max Number of Torn Transaction Log Records", ""),
    MAG_STRIPE_CVM_CAPABILITY_CVM_REQUIRED("df811e", TagValueTypeEnum.BINARY, "Mag-stripe CVM Capability – CVM Required", ""),
    SECURITY_CAPABILITY("df811f", TagValueTypeEnum.BINARY, "Security Capability", ""),
    TERMINAL_ACTION_CODE_DEFAULT("df8120", TagValueTypeEnum.BINARY, "Terminal Action Code – Default", ""),
    TERMINAL_ACTION_CODE_DENIAL("df8121", TagValueTypeEnum.BINARY, "Terminal Action Code – Denial", ""),
    TERMINAL_ACTION_CODE_ONLINE("df8122", TagValueTypeEnum.BINARY, "Terminal Action Code – Online", ""),
    READER_CONTACTLESS_FLOOR_LIMIT("df8123", TagValueTypeEnum.BINARY, "Reader Contactless Floor Limit", ""),
    READER_CONTACTLESS_TRANSACTION_LIMIT_NO_OD_CVM("df8124", TagValueTypeEnum.BINARY, "Reader Contactless Transaction Limit (No On-device CVM)", ""),
    READER_CONTACTLESS_TRANSACTION_LIMIT_OD_CVM("df8125", TagValueTypeEnum.BINARY, "Reader Contactless Transaction Limit (On-device CVM)", ""),
    READER_CVM_REQUIRED_LIMIT("df8126", TagValueTypeEnum.BINARY, "Reader CVM Required Limit", ""),
    TIME_OUT_VALUE("df8127", TagValueTypeEnum.BINARY, "TIME_OUT_VALUE", ""),
    IDS_STATUS("df8128", TagValueTypeEnum.BINARY, "IDS Status", ""),
    OUTCOME_PARAMETER_SET("df8129", TagValueTypeEnum.BINARY, "Outcome Parameter Set", ""),
    DD_CARD_TRACK1("df812a", TagValueTypeEnum.TEXT, "DD Card (Track1)", ""),
    DD_CARD_TRACK2("df812b", TagValueTypeEnum.TEXT, "DD Card (Track2)", ""),
    MAG_STRIPE_CVM_CAPABILITY_NO_CVM_REQUIRED("df812c", TagValueTypeEnum.BINARY, "Mag-stripe CVM Capability – No CVM Required", ""),
    MESSAGE_HOLD_TIME("df812d", TagValueTypeEnum.BINARY, "Message Hold Time", ""),
    HOLD_TIME_VALUE("df8130", TagValueTypeEnum.BINARY, "Indicates the time that the field is to be turned off after the\n" +
            "transaction is completed", ""),
    PHONE_MESSAGE_TABLE("df8131", TagValueTypeEnum.BINARY, "Phone Message Table", ""),
    MINIMUM_RELAY_RESISTANCE_GRACE_PERIOD("df8132", TagValueTypeEnum.BINARY, "Minimum Relay Resistance Grace Period", ""),
    MAXIMUM_RELAY_RESISTANCE_GRACE_PERIOD("df8133", TagValueTypeEnum.BINARY, "Maximum Relay Resistance Grace Period", ""),
    TERMINAL_EXPECTED_TRANSMISSION_TIME_C_APDU("df8134", TagValueTypeEnum.BINARY, "Terminal Expected Transmission Time For Relay Resistance C-\n" +
            "APDU", ""),
    TERMINAL_EXPECTED_TRANSMISSION_TIME_R_APDU("df8135", TagValueTypeEnum.BINARY, "Terminal Expected Transmission Time For Relay Resistance R-\n" +
            "APDU", ""),
    RELAY_RESISTANCE_ACCURACY_THRESHOLD("df8136", TagValueTypeEnum.BINARY, "Relay Resistance Accuracy Threshold", ""),
    RELAY_RESISTANCE_TRANSMISSION_TIME_MISMATCH_THRESHOLD("df8137", TagValueTypeEnum.BINARY, "Relay Resistance Transmission Time Mismatch Threshold", ""),
    DEVICE_ESTIMATED_TRANSMISSION_TIME_FOR_RELAY_RESISTANCE_RAPDU("DF8305", TagValueTypeEnum.BINARY, "Device Estimated Transmission Time For Relay Resistance R-APDU", ""),
    TERMINAL_RELAY_RESISTANCE_ENTROPY("DF8301", TagValueTypeEnum.BINARY, "Terminal Relay Resistance Entropy", ""),
    DEVICE_RELAY_RESISTANCE_ENTROPY("DF8302", TagValueTypeEnum.BINARY, "Device Relay Resistance Entropy", ""),
    MIN_TIME_FOR_PROCESSING_RELAY_RESISTANCE_APDU("DF8303", TagValueTypeEnum.BINARY, "Min Time For Processing Relay Resistance APDU", ""),
    MAX_TIME_FOR_PROCESSING_RELAY_RESISTANCE_APDU("DF8304", TagValueTypeEnum.BINARY, "Max Time For Processing Relay Resistance APDU", ""),
    MEASURED_RELAY_RESISTANCE_PROCESSING_TIME("DF8306", TagValueTypeEnum.BINARY, "Measured Relay Resistance Processing Time", ""),
    RRP_COUNTER("DF8307", TagValueTypeEnum.BINARY, "RRP Counter", ""),

    PHOS_STATUS_CHECK_ENABLED("DF808001", TagValueTypeEnum.BINARY, "If != 0 status check is enabled. Phos app private tag.", ""),
    PHOS_ZERO_AMOUNT_ALLOWED("DF808002", TagValueTypeEnum.BINARY, "If != 0 zero amount is allowed. Phos app private tag.", ""),
    PHOS_EXTENDED_SELECTION_SUPPRORTED("DF808003", TagValueTypeEnum.BINARY, "If != 0 extended selection is supported by the kernel. Phos app private tag.", ""),
    PHOS_TTQ_CONFIGURATION("DF808004", TagValueTypeEnum.TEXT, "Json encoded TtqConfiguration. Phos app private tag.", ""),
    PHOS_VISA_AUC_CASHBACK_ENABLED("DF808005", TagValueTypeEnum.BINARY, "If != 0 Visa's AUC-Cashback check is enabled. Phos app private tag.", ""),
    PHOS_VISA_AUC_MANUAL_CASH_ENABLED("DF808006", TagValueTypeEnum.BINARY, "If != 0 Visa's AUC-Manual cash check is enabled. Phos app private tag.", ""),
    PHOS_VISA_POS_ENTRY_MODE("DF808050", TagValueTypeEnum.BINARY, "POS entry mode", ""),
    PHOS_VISA_TERMINAL_ENTRY_CAPABILITY("DF808051", TagValueTypeEnum.BINARY, "Terminal entry capability", ""),
    PHOS_TRACK2_ENCRYPTED_DATA("DF808052", TagValueTypeEnum.BINARY, "Phos Track 2 encrypted data", ""),
    PHOS_TRACK2_ENCRYPTED_IV("DF808053", TagValueTypeEnum.BINARY, "Phos Track 2 encrypted IV", ""),
    PHOS_TRACK1_ENCRYPTED_DATA("DF808054", TagValueTypeEnum.BINARY, "Phos Track 1 encrypted data", ""),
    PHOS_TRACK1_ENCRYPTED_IV("DF808055", TagValueTypeEnum.BINARY, "Phos Track 1 encrypted IV", ""),
    PHOS_PAN_ENCRYPTED_DATA("DF808056", TagValueTypeEnum.BINARY, "PAN encrypted data", ""),
    PHOS_PAN_ENCRYPTED_IV("DF808057", TagValueTypeEnum.BINARY, "PAN encrypted IV", ""),
    PHOS_TRACK2_EQV_ENCRYPTED_DATA("DF808058", TagValueTypeEnum.BINARY, "Phos Track 2 equivalent encrypted data", ""),
    PHOS_TRACK2_EQV_ENCRYPTED_IV("DF808059", TagValueTypeEnum.BINARY, "Phos Track 2 equivalent encrypted IV", ""),

    PHOS_OBFUSCATION1("DF808099", TagValueTypeEnum.BINARY, "Phos Track 2 equivalent encrypted Data", ""),
    PHOS_OBFUSCATION2("DF808098", TagValueTypeEnum.BINARY, "Phos Track 2 equivalent encrypted seed", ""),
    PHOS_OBFUSCATION3("DF808097", TagValueTypeEnum.BINARY, "Unpredictable number", ""),


    TORN_RECORD("ff8101", TagValueTypeEnum.BINARY, "Torn Record", ""),
    TAGS_TO_WRITE_BEFORE_GEN_AC("ff8102", TagValueTypeEnum.BINARY, "Tags To Write Before Gen AC", ""),
    TAGS_TO_WRITE_AFTER_GEN_AC("ff8103", TagValueTypeEnum.BINARY, "Tags To Write After Gen AC", ""),
    DATA_TO_SEND("ff8104", TagValueTypeEnum.BINARY, "Data To Send", ""),
    DATA_RECORD("ff8105", TagValueTypeEnum.BINARY, "Data Record", ""),
    DISCRETIONARY_DATA("ff8106", TagValueTypeEnum.BINARY, "Discretonary data", "");


    private static Map<Byte, Entry> map = new HashMap<>();

    static {
        for (final EmvTag tag : EmvTag.values()) {
            initMap(map, tag, 0);
        }
    }

    private final String name;
    private final byte[] idBytes;
    private final String description;
    private final TagValueTypeEnum tagValueType;
    private final TagClass tagClass;
    private final TagTypeEnum type;


    EmvTag(final String name,
           final byte[] idBytes,
           final String description,
           final TagValueTypeEnum tagValueType
    ) {

        this.name = name;
        this.idBytes = idBytes;
        this.description = description;
        this.tagValueType = tagValueType;

        if (ByteUtils.matchBitByBitIndex(this.idBytes[0], 5)) {
            type = TagTypeEnum.CONSTRUCTED;
        } else {
            type = TagTypeEnum.PRIMITIVE;
        }

        // Bits 8 and 7 of the first byte of the tag field indicate a class.
        // The value 00 indicates a data object of the universal class.
        // The value 01 indicates a data object of the application class.
        // The value 10 indicates a data object of the context-specific class.
        // The value 11 indicates a data object of the private class.
        final byte classValue = (byte) (this.idBytes[0] >>> 6 & 0x03);
        switch (classValue) {
            case (byte) 0x01:
                this.tagClass = TagClass.APPLICATION;
                break;
            case (byte) 0x02:
                this.tagClass = TagClass.CONTEXT_SPECIFIC;
                break;
            case (byte) 0x03:
                this.tagClass = TagClass.PRIVATE;
                break;
            default:
                this.tagClass = TagClass.UNIVERSAL;
                break;
        }
    }


    EmvTag(final String id, final TagValueTypeEnum tagValueType, final String name, final String description) {
        this(name, ByteUtils.fromString(id), description, tagValueType);
    }


    private static void initMap(final Map<Byte, Entry> map, final EmvTag tag, int level) {
        Entry e = map.get(tag.getTagBytes()[level]);
        if (e == null) {
            e = new Entry(tag);
            map.put(tag.getTagBytes()[level], e);
        }

        if (tag.getTagBytes().length > level + 1) {
            initMap(e.map, tag, ++level);
        }
    }


    public static EmvTag resolveById(final byte[] tagId) {
        EmvTag ret = null;

        if (tagId.length >= 1) {
            ret = findInMap(map, tagId, 0);

            if (ret == null) {
                ret = EmvTag.UNKNOWN;
            }
        }

        return ret;
    }


    private static EmvTag findInMap(final Map<Byte, Entry> map, final byte[] tagId, int level) {
        final Entry e = map.get(tagId[level]);
        if (e != null) {
            if (tagId.length == level + 1) {
                return e.tag;
            } else {
                return findInMap(e.map, tagId, ++level);
            }
        } else {
            return null;
        }
    }


    public int getNumTagBytes() {
        return idBytes.length;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Tag[");
        sb.append(ByteUtils.toHexString(getTagBytes()));
        sb.append("] Name=");
        sb.append(getName());
//        sb.append(", TagType=");
//        sb.append(getType());
//        sb.append(", ValueType=");
//        sb.append(getTagValueType());
//        sb.append(", Class=");
//        sb.append(tagClass);
        return sb.toString();
    }


    public String getName() {
        return name;
    }


    public byte[] getTagBytes() {
        return idBytes.clone();
    }


    public String getDescription() {
        return description;
    }


    public TagValueTypeEnum getTagValueType() {
        return tagValueType;
    }

    public TagClass getTagClass() {
        return tagClass;
    }


    public TagTypeEnum getType() {
        return type;
    }


    public boolean isConstructed() {
        return type == TagTypeEnum.CONSTRUCTED;
    }


    enum TagClass {
        UNIVERSAL, APPLICATION, CONTEXT_SPECIFIC, PRIVATE
    }


    private static class Entry {
        private EmvTag tag;
        private Map<Byte, Entry> map = new HashMap<>();


        private Entry(final EmvTag tag) {
            this.tag = tag;
        }

    }


    /**
     * As defined in Annex B of [EMV Book 3], the tag is Private class if bits b7 and b8 of the first byte of the tag are both set
     * to 1b.
     */
    public boolean isPrivateClass() {
        BigInteger bi = BigInteger.valueOf(getTagBytes()[0]);

        return bi.testBit(6) && bi.testBit(7);
    }

}