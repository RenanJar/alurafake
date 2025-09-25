package br.com.alura.AluraFake.domain.error;



public enum ProblemType {

    RESOURCE_NOT_FOUND(
            "/resource-not-found",
            "Resource not found",
            "The requested resource was not found."
    ),

    INVALID_INSTRUCTOR_ROLE(
    "/invalid-instructor-role",
            "Invalid instructor role",
            "You do not have permission to perform this action with your current role."
    );

    private final String path;
    private final String title;
    private final String message;

    ProblemType(String path, String title, String message) {
        this.path = path;
        this.title = title;
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }
}
