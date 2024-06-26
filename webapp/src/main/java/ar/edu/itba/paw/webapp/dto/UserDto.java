package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.function.Function;

public class UserDto {

    private String username;
    private URI self;
    private String password; // write-only
    private URI reportedIssues;
    private URI assignedIssues;

    public static Function<User, UserDto> mapper(UriInfo uriInfo) {
        return u -> fromUser(uriInfo, u);
    }
    // curry (fijo un argumento) f(a,b) -> g(b) == f(a,b)

    public static UserDto fromUser(UriInfo uriInfo, User u) {
        final UserDto dto = new UserDto();
        dto.username = u.getUsername();
        dto.self = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(u.getUserId())).build();

        dto.reportedIssues = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(u.getUserId()))
                .path("issues").queryParam("reportedBy", u.getUserId()).build();

        dto.assignedIssues = uriInfo.getBaseUriBuilder().path("users").path(String.valueOf(u.getUserId()))
                .path("issues").queryParam("assignedTo", u.getUserId()).build();

        return dto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public URI getReportedIssues() {
        return reportedIssues;
    }

    public void setReportedIssues(URI reportedIssues) {
        this.reportedIssues = reportedIssues;
    }

    public URI getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(URI assignedIssues) {
        this.assignedIssues = assignedIssues;
    }
}
