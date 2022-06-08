package net.study.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static net.study.security.ApplicationUserPermission.COURSE_READ;
import static net.study.security.ApplicationUserPermission.COURSE_WRITE;
import static net.study.security.ApplicationUserPermission.STUDENT_READ;
import static net.study.security.ApplicationUserPermission.STUDENT_WRITE;

public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private Set<ApplicationUserPermission> permission;

    ApplicationUserRole(Set<ApplicationUserPermission> permission){
        this.permission = permission;
    }

    public Set<ApplicationUserPermission> getPermission() {
        return permission;
    }

    public Set<SimpleGrantedAuthority> getSimpleGrantedAuthority(){
        Set<SimpleGrantedAuthority> pers = getPermission().stream()
                .map(per -> new SimpleGrantedAuthority(per.getPermission()))
                .collect(Collectors.toSet());
        pers.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return pers;
    }

}
