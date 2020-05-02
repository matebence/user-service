package com.blesk.userservice.Value;

public class Messages {

    public static final String USERS_FIRST_ACCOUNT_NOT_NULL = "Nezadali ste identifikačné čislo používateľského konta";
    public static final String USERS_FIRST_NAME_NOT_NULL = "Nezadali ste meno";
    public static final String USERS_FIRST_NAME_SIZE = "Meno je príliž krátke alebo dlhé";
    public static final String USERS_LAST_NAME_NOT_NULL = "Nezadali ste priezvisko";
    public static final String USERS_LAST_NAME_SIZE = "Priezvisko je príliž krátke alebo dlhé";
    public static final String USERS_BALANCE_NOT_NULL = "Nezadali ste sumu, ktorú chcete previesť na účet";
    public static final String USERS_BALANCE_MIN = "Minimálna suma je 1 Eur";
    public static final String USERS_BALANCE_MAX = "Maximálna suma je 99 999 Eur";
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



    public static final String CONTAINS_VALIDATOR_DEFAULT = "Tento údaj neeviduje v databáze";
    public static final String PAGINATION_ERROR = "Požiadavku sa nepodarilo spracovať, chyba stránkovania";



    public static final String CREATE_GENDER = "Nový názov pohlavia sa nepodarilo vytvoriť";
    public static final String DELETE_GENDER = "Odstránenie názov pohlavia sa nepodarilo";
    public static final String UPDATE_GENDER = "Aktualizovanie názov pohlavia sa nepodarilo";
    public static final String GET_GENDER = "Ľutujeme, názov pohlavia neexistuje";
    public static final String GET_ALL_GENDERS = "Nenašiel sa žiadný záznam pre názov pohlavia";

    public static final String CREATE_PLACE = "Nový záznam adresi sa nepodarilo vytvoriť";
    public static final String DELETE_PLACE = "Odstránenie adresi sa nepodarilo";
    public static final String UPDATE_PLACE = "Aktualizovanie adresi sa nepodarilo";
    public static final String GET_PLACE = "Ľutujeme, adresa neexistuje";
    public static final String GET_ALL_PLACES = "Nenašiel sa žiadný záznam pre názov adresi";

    public static final String CREATE_USER = "Profil pre používateľské konto sa nepodarilo vytvoriť";
    public static final String DELETE_USER = "Odstránenie profilu sa nepodarilo";
    public static final String UPDATE_USER = "Aktualizovanie profilu sa nepodarilo";
    public static final String GET_USER = "Ľutujeme, ale profil neexistuje";
    public static final String GET_ALL_USERS = "Nenašiel sa žiadný profil";
}