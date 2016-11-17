/*
 Copyright 2013 Red Hat, Inc. and/or its affiliates.

 This file is part of lightblue.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.redhat.lightblue.metadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Keeps a set of roles that can perform a certain operation
 */
public class Access extends MetadataObject {

    private final HashSet<String> values = new HashSet<>();

    /**
     * Default ctor
     */
    public Access() {
    }

    /**
     * Sets the roles. The given collection contents are copied to internal
     * storage.
     */
    public void setRoles(Collection<String> roles) {
        values.clear();
        if (roles != null) {
            values.addAll(roles);
        }
    }

    /**
     * Sets the roles.
     */
    public void setRoles(String... roles) {
        values.clear();
        if (roles != null) {
            for (String x : roles) {
                values.add(x);
            }
        }
    }

    /**
     * Sets the roles.
     */
    public void setRoles(Access a) {
        setRoles(a.values);
    }

    /**
     * Adds all the roles to the destination set
     */
    public void addRolesTo(Set<String> dest) {
        dest.addAll(values);
    }

    /**
     * Retrieves the roles. A copy of the internal storage is returned.
     */
    @SuppressWarnings("unchecked")
    public Set<String> getRoles() {
        return (Set<String>) values.clone();
    }

    /**
     * Returns if there are no roles defined.
     */
    public boolean isEmpty() {
        return values.isEmpty();
    }

    /**
     * Returns if anyone can perform this operation
     */
    public boolean isAnyone() {
        return containsIgnoreCase(MetadataConstants.ROLE_ANYONE);
    }

    /**
     * Returns if noone can perform this operation
     */
    public boolean isNoone() {
        return containsIgnoreCase(MetadataConstants.ROLE_NOONE);
    }

    /**
     * Returns if the given role can perform this operation
     */
    public boolean hasAccess(String role) {
        if (isNoone()) {
            return false;
        }
        return isAnyone() || containsIgnoreCase(role);
    }

    /**
     * Returns if a caller with the given roles can perform this operation
     */
    public boolean hasAccess(Collection<String> roles) {
        if (isNoone()) {
            return false;
        }
        if (isAnyone()) {
            return true;
        }
        for (String x : roles) {
            if (containsIgnoreCase(x)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsIgnoreCase(String role){
        for(String value : new ArrayList<>(values)) {
            if(value.equalsIgnoreCase(role)) {
                return true;
            }
        }

        return false;
    }

}
