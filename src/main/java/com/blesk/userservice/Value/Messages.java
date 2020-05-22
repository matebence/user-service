package com.blesk.userservice.Value;

public class Messages {

    public static final String TYPE_MISMATCH_EXCEPTION = "Nesprávný formát URL adresi";
    public static final String REQUEST_BODY_NOT_FOUND_EXCEPTION = "Prázdna požiadavka";
    public static final String PAGE_NOT_FOUND_EXCEPTION = "Je nám ľúto, ale požadovaná stránka nebola nájdená";
    public static final String NULL_POINTER_EXCEPTION = "Ľutujeme, ale nastala chyba";
    public static final String AUTH_EXCEPTION = "Ľutujeme, ale stránka nie je k dispozícií";
    public static final String AUTH_REQUIRED_EXCEPTION = "Prístup odmietnutý";
    public static final String SQL_EXCEPTION = "Operácia sa neuskutočnila";
    public static final String SERVER_EXCEPTION = "Ľutujeme, ale server je momentálne nedostupný";
    public static final String STRIPE_EXCEPTION = "Ľutujeme, ale tranzakcia bola neúpešná";
    public static final String EXCEPTION = "Nastala neočakávaná chyba";



    public static final String USERS_FIRST_ACCOUNT_NOT_NULL = "Nezadali ste identifikačné čislo používateľského konta";
    public static final String USERS_FIRST_NAME_NOT_NULL = "Nezadali ste meno";
    public static final String USERS_FIRST_NAME_SIZE = "Meno je príliž krátke alebo dlhé";
    public static final String USERS_LAST_NAME_NOT_NULL = "Nezadali ste priezvisko";
    public static final String USERS_LAST_NAME_SIZE = "Priezvisko je príliž krátke alebo dlhé";
    public static final String USERS_BALANCE_NOT_NULL = "Nezadali ste sumu, ktorú chcete previesť na účet";
    public static final String USERS_BALANCE_RANGE = "Minimálna suma je 10 Eur a maximálna suma je 99 999 Eur";
    public static final String USERS_TEL_NOT_NULL = "Nezadali ste telefonné číslo";
    public static final String USERS_TEL_SIZE = "Telefonné číslo je príliž krátke alebo dlhé";
    public static final String USERS_IMG_SIZE = "Názov obrázka je príliž krátke alebo dlhé";
    public static final String USERS_GENDER_COTNAINS = "Typ pohlavia neexistuje";
    public static final String USERS_GENDER_NOT_NULL = "Nezadali ste pohlavie";
    public static final String USERS_GENDER_SIZE = "Názov pohlavia je príliž krátke alebo dlhé";

    public static final String GENDERS_NAME_NOT_NULL = "Nezadali ste názov pohlavia";
    public static final String GENDERS_NAME_SIZE = "Názov pohlavia je príliž krátke alebo dlhé";

    public static final String PLACES_COUNTRY_NOT_NULL = "Nezadali ste názov štátu";
    public static final String PLACES_COUNTRY_SIZE = "Názov štátu je príliž krátke alebo dlhé";
    public static final String PLACES_REGION_NOT_NULL = "Nezadali ste názov okresu";
    public static final String PLACES_REGION_SIZE = "Názov okresu je príliž krátke alebo dlhé";
    public static final String PLACES_DISTRICT_NOT_NULL = "Nezadali ste názov kraja";
    public static final String PLACES_DISTRICT_SIZE = "Názov kraja je príliž krátke alebo dlhé";
    public static final String PLACES_PLACE_NOT_NULL = "Nezadali ste bydlisko";
    public static final String PLACES_PLACE_SIZE = "Názov bydliska je príliž krátke alebo dlhé";
    public static final String PLACES_STREET_NOT_NULL = "Nezadali ste adresu";
    public static final String PLACES_STREET_SIZE = "Názov adresi je príliž krátký alebo dlhý";
    public static final String PLACES_ZIP_NOT_NULL = "Nezadali ste PSČ";
    public static final String PLACES_CODE_NOT_NULL = "Nezadali ste kód štátu";
    public static final String PLACES_CODE_SIZE = "Kód štátu je príliž krátke alebo dlhé";

    public static final String PAYOUTS_IBAN_NOT_NULL = "Nezadali ste IBAN";
    public static final String PAYOUTS_AMOUNT_NOT_NULL = "Nezadali ste čiastku na prevod";
    public static final String PAYOUTS_AMOUNT_RANGE = "Minimálna čiastka je 10 Eur a maximálna čiastka je 99 999 Eur";
    public static final String PAYOUTS_ACCAPTED_NOT_NULL = "Nezadali stav tranzakcie";

    public static final String PAYMENTS_CREDIT_CARD_NOT_NULL = "Nezadali ste kreditnú kartu";
    public static final String PAYMENTS_CHARGE_NOT_NULL = "Identifikacia platby nebola zadaná";
    public static final String PAYMENTS_AMOUNT_NOT_NULL = "Nezadali ste čiastku na prevod";
    public static final String PAYMENTS_AMOUNT_RANGE = "Minimálna čiastka je 10 Eur a maximálna čiastka je 99 999 Eur";
    public static final String PAYMENTS_CURRENCY_NOT_NULL = "Nezadali ste menu";
    public static final String PAYMENTS_REFUNDED_NOT_NULL = "Stav o vráteni penazí nebola zadaná";



    public static final String CREATE_GENDER = "Nový názov pohlavia sa nepodarilo vytvoriť";
    public static final String DELETE_GENDER = "Odstránenie názov pohlavia sa nepodarilo";
    public static final String UPDATE_GENDER = "Aktualizovanie názov pohlavia sa nepodarilo";
    public static final String GET_GENDER = "Ľutujeme, názov pohlavia neexistuje";
    public static final String GET_ALL_GENDERS = "Nenašiel sa žiadný záznam pre názov pohlavia";

    public static final String CREATE_PAYOUT = "Prevod penazí bola neúspešne, skúste znova";
    public static final String DELETE_PAYOUT = "Odstránenie prevodu bola neúspešná";
    public static final String UPDATE_PAYOUT = "Aktualizovanie prevodu bola neúspešná";
    public static final String GET_PAYOUT = "Informácia o prevode penazí nebola nájdená";
    public static final String GET_ALL_PAYOUTS = "Nenašli sa žiadné informacie a prevode penazí";

    public static final String CREATE_PAYMENT = "Peniaze sa úspešne pripísali na účet";
    public static final String DELETE_PAYMENT = "Odstránenie pripísanej sumy na účet sa nepodarilo";
    public static final String UPDATE_PAYMENT = "Aktualizovanie pripísanej sumy na účet sa nepodarilo";
    public static final String GET_PAYMENT = "Informácia o prepísanej sumy sa nenašla";
    public static final String GET_ALL_PAYMENTS = "Nenašli sa žiadné informácie o pripísaných súm";

    public static final String CREATE_USER = "Profil pre používateľské konto sa nepodarilo vytvoriť";
    public static final String DELETE_USER = "Odstránenie profilu sa nepodarilo";
    public static final String UPDATE_USER = "Aktualizovanie profilu sa nepodarilo";
    public static final String GET_USER = "Ľutujeme, ale profil neexistuje";
    public static final String GET_ALL_USERS = "Nenašiel sa žiadný profil";

    public static final String CREATE_CACHE = "Nepodarilo sa vytvoriť/aktualizovať cache pre Model Users";
    public static final String DELETE_CACHE = "Nepodarilo sa odstrániť cache pre Model Users";
    public static final String FIND_CACHE = "Názov pohlavia uz existuje";
    public static final String NOT_FOUND_CACHE = "Názov pohlavia uz existuje";



    public static final String USER_ACCOUNT_ID_UNIQUE = "Identifikačné číslo používateľského konta už existuje";
    public static final String USER_TEL_UNIQUE = "Telefóne číslo už existuje";
    public static final String GENDER_NAME_UNIQUE = "Názov pohlavia uz existuje";
    public static final String PAYMENT_CREDIT_CARD = "Kreditná karta už existuje";
    public static final String PAYMENT_CHARGE = "Platba už bola vykonaná";
    public static final String PAYMENT_REFUND = "Peniaze už boli vrátené zákaznikovi";
    public static final String PAYOUTS_IBAN = "IBAN už existuje";



    public static final String CONTAINS_VALIDATOR_DEFAULT = "Tento údaj neeviduje v databáze";
    public static final String PAGINATION_ERROR = "Požiadavku sa nepodarilo spracovať, chyba stránkovania";
    public static final String SEARCH_ERROR = "Kritériám nevyhoveli žiadné záznamy";
    public static final String UNIQUE_FIELD_DEFAULT = "Obsah pola nie je jedinečné";
}