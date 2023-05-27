package commons.spring.security

public const val BEARER_PREFIX: String = "Bearer "
public const val ROLE_USER: String = "ROLE_USER"
public const val ROLE_SERVICE: String = "ROLE_SERVICE"
public const val AUTHORIZE_USER: String = "hasRole('ROLE_USER')"
public const val AUTHORIZE_SERVICE: String = "hasRole('ROLE_SERVICE')"