package com.spring_boot.caching.messageConstant;

public class MessageConstant {

    public static final String SUCCESSFULLY = "Successfully";
    public static final String RESET_PASSWORD_SUCCESSFULLY= "Password reset has been successfully";
    public static final String ALL = "ALL";

    public static class ROLE {
        public final static String ADMIN = "ADMIN";
        public final static String USER = "USER";
        public final static Long USER_ROLE_ID = 2L;

        public static final String ROLE_CREATED_SUCCESSFULLY = "Role has been created";
        public static final String ROLE_NOT_FOUND = "Role could not be found";
        public static final String ROLE_UPDATED_SUCCESSFULLY = "Role has been updated";
        public static final String ROLE_DELETED_SUCCESSFULLY = "Role has been deleted";
        public static final String ROLE_HAS_USER = "Role has user delete user first";
        public static final String PERMISSION_NOT_FOUND = "Permission has not been found";
    }

    public static class CREDENTIAL{
        public static final String VERIFY_CODE_HAS_BEEN_EXPRIED = "Verified code has expired";
        public static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email or password";
        public final static String PASSWORD_NOT_MATCH = "Password has not been match";
    }

    public static class ROLEPROPERTY{
        public static final String CODE_HAS_EXISTING = "Code has already existing";
        public static final String NAME_HAS_EXISTRING = "Name has already existing";
    }

    public static class REGISTERPROPERTY{
        public final static String EMAIL_IS_EXISTING = "Email has already existing";
        public final static String EMAIL_HAS_NOT_BEEN_FOUND = "Email has not been found";
        public final static String USERNAME_IS_NOT_VALID = "User name is not valid";
        public final static String PHONE_IS_NOT_VALID = "Phone Number is not valid";
        public final static String REGISTER_HAS_BEEN_SUCCESSFULLY = "You register has been successfully";
    }

    public static class CATEGORY {
        public final static String NAME_IS_NULL = "Name is null";
        public final static String CATEGORY_ID_IS_NULL = "Category id is null";
        public final static String USER_ID_IS_NULL = "UserID is null";
        public final static String MEDIA_ID_IS_NULL = "MediaID is null";
        public static final String CATEGORY_HAS_BEEN_CREATED = "Category has been created";
        public static final String CATEGORY_HAS_BEEN_UPDATED = "Category has been updated";
        public static final String CATEGORY_COULD_NOT_BE_FOUND = "Category could not be found";
        public static final String CATEGORY_HAS_BEEN_DELETED = "Category has been deleted";
    }

    public static class POST{
        public static final String POST_HAS_BEEN_CREATED = "Post has been created";
        public static final String POST_COULD_NOT_BE_FOUND = "Post could nt be found";
        public static final String POST_HAS_BEEN_DELETED = "Post has been deleted";
        public static final String POST_TITLE_IS_NULL = "Post title is null";
        public static final String POST_DESCRIPTION_IS_NULL = "Post description is null";
        public static final String POST_PUBLIC_AT_IS_NULL = "Post public at is null";
        public static final String POST_STATUS_IS_NULL = "Post status is null";
        public static final String POST_ID_NOT_FOUND = "Post id not found";
    }
    public static class USER{
        public static final String USER_ID_IS_NULL = "User id is null";
        public static final String USER_HAS_BEEN_CREATED = "User has been created";
        public static final String USER_HAS_BEEN_UPDATED = "User has been updated";
        public static final String USER_HAS_BEEN_DELETED = "User has been deleted";
        public static final String USER_NOT_FOUND = "User could not be found";
    }
    public static class FILEMEDIA{
        public static final String FILE_MEDIA_NOT_FOUNT = "File media not fount";
        public static final String FILE_MEDIA_ID_IS_NULL = "File media id is null";
        public static final String FILE_MEDIA_NAME_IS_NULL = "File media name is null";
        public static final String FILE_MEDIA_TYPE_IS_NULL = "File media type is null";
        public static final String FILE_MEDIA_SIZE_IS_NULL = "File media size is null";
        public static final String FILE_MEDIA_URL_IS_NULL = "File media url is null";
        public static final String FILE_MEDIA_HAS_BEEN_CREATED ="File media has been created";
        public static final String FILE_MEDIA_HAS_BEEN_DELETE = "File media has been deleted";
        public static final String FILE_PATH_HAS_NOT_FOUND = "File path has not found";
    }
    public static class TOPIC{
        public static final String TOPIC_ID_IS_NULL = "Topic id is null";
        public static final String TOPIC_NOT_FOUND = "Topic not found";
        public static final String TOPIC_HAS_BEEN_CREATE = "Topic has been create";
        public static final String TOPIC_HAVE_BEEN_DELETED = "Topic has been delete";
    }

}

