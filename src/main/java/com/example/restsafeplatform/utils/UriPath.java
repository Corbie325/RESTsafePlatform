package com.example.restsafeplatform.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UriPath {

    // utils

    public static final String API_VERSION = "1";

    public static final String API_PATH_PREFIX = "/api/" + API_VERSION;

    public static final String SEND_MESSAGE_MP = "/func/command";

    public static final String ADD_USER_QUESTION = "/question";

    public static final String ADD_CONFIDANT = "/confidant";

    public static final String ADD_LOCATION = "/location";

    // Пользователи

    public static final String ADMIN_REGISTRATION = "/admin_registration";

    public static final String USER_REGISTRATION = "/registration";

    public static final String GET_ALL_USER = "/admin";

    public static final String GET_USER_BY_ID = GET_ALL_USER + "/{id}";

    public static final String GET_USER_BY_TOKEN = "/get_user";

    public static final String DELETE_USER_BY_ID = GET_ALL_USER + "/{id}";

    public static final String ADD_FAST_CODE = "/fast/{code}";

    // Лицензия

    public static final String ADD_LICENSE = "/license";

    // Оружие

    public static final String ADD_WEAPON = "/weapon";

    public static final String GET_ALL_WEAPON = "/weapon_list";

    public static final String ADD_ALIAS = "/weapon/alias";

    // Блок

    public static final String ADD_UNIT = "/unit";

    public static final String GET_UNIT_STATUS = ADD_UNIT + "/{serial}";

    public static final String GET_ALL_UNIT = "/ulist";

    public static final String ACTIVATE_UNIT = "/unit/activate/{serial}";

    // Команды блока

    public static final String SET_COLOR = "/color";

    public static final String GET_COORDINATES = "/coordinates";

    public static final String SET_BC_SETTINGS = "/bc_settings";

    public static final  String SET_MODE = "/mode";

}
