package address.model.datatypes.person;

import address.model.datatypes.ExtractableObservables;
import address.model.datatypes.tag.Tag;
import address.util.collections.UnmodifiableObservableList;
import commons.DateTimeUtil;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Allows read-only access to the Person domain object's data.
 */
public interface ReadOnlyPerson extends ExtractableObservables {

    /**
     * @param key ReadOnlyPerson with ID of the element you wish to remove from {@code col}
     * @return whether {@code col} was changed as a result of this operation
     */
    static boolean removeOneById(Collection<? extends ReadOnlyPerson> col, ReadOnlyPerson key) {
        return removeOneById(col, key.getId());
    }

    /**
     * @return whether {@code col} was changed as a result of this operation
     */
    static <P extends ReadOnlyPerson> boolean removeOneById(Collection<P> col, int id) {
        final Optional<P> toRemove = findById(col, id);
        if (toRemove.isPresent()) {
            return col.remove(toRemove.get());
        } else {
            return false;
        }
    }

    /**
     * @see #removeAllById(Collection, Collection)
     * @param col collection to remove from
     * @param keys collection of ReadOnlyPersons with the IDs of the those you wish to remove from {@code col}
     * @return whether {@code col} was changed as a result of this operation
     */
    static boolean removeAllWithSameIds(Collection<? extends ReadOnlyPerson> col,
                                        Collection<? extends ReadOnlyPerson> keys) {
        return removeAllById(col, keys.stream().map(e -> e.getId()).collect(Collectors.toList()));
    }

    /**
     * @see Collection#removeAll(Collection)
     * @param ids collection of IDs of the persons you wish to remove from {@code col}
     * @return whether {@code col} was changed as a result of this operation
     */
    static boolean removeAllById(Collection<? extends ReadOnlyPerson> col,
                                 Collection<Integer> ids) {
        final Set<Integer> idSet = new HashSet<>(ids);
        return col.removeAll(col.stream().filter(p -> idSet.contains(p.getId())).collect(Collectors.toList()));
    }

    /**
     * @return the first element found in {@code col} with same id as {@code key}
     */
    static <P extends ReadOnlyPerson> Optional<P> findById(Collection<P> col, ReadOnlyPerson key) {
        return findById(col, key.getId());
    }

    /**
     * @return the first element found in {@code col} with same id as {@code id}
     */
    static <P extends ReadOnlyPerson> Optional<P> findById(Collection<P> col, int id) {
        return col.stream().filter(p -> id == p.getId()).findFirst();
    }

    /**
     * @see #containsById(Collection, int)
     */
    static boolean containsById(Collection<? extends ReadOnlyPerson> col, ReadOnlyPerson key) {
        return containsById(col, key.getId());
    }

    /**
     * @see Collection#contains(Object)
     */
    static boolean containsById(Collection<? extends ReadOnlyPerson> col, int id) {
        return col.stream().anyMatch(e -> id == e.getId());
    }

    /**
     * Remote-assigned (canonical) ids are positive integers.
     * Locally-assigned temporary ids are negative integers.
     * 0 is reserved for ID-less Person data containers.
     */
    int getId();
    default String idString() {
        return hasConfirmedRemoteID() ? "#" + getId() : "#TBD";
    }
    /**
     * @see #getId()
     */
    default boolean hasConfirmedRemoteID() {
        return getId() > 0;
    }

    String getFirstName();
    String getLastName();
    /**
     * @return first-last format full name
     */
    default String fullName() {
        return getFirstName() + ' ' + getLastName();
    }

    String getGithubUsername();

    default URL profilePageUrl(){
        try {
            return new URL("https://github.com/" + getGithubUsername());
        } catch (MalformedURLException e) {
            try {
                return new URL("https://github.com");
            } catch (MalformedURLException e1) {
                assert false;
            }
        }
        return null;
    }
    default Optional<String> githubProfilePicUrl() {
        if (getGithubUsername().length() > 0) {
            String profilePicUrl = profilePageUrl().toExternalForm() + ".png";
            return Optional.of(profilePicUrl);
        }
        return Optional.empty();
    }

    String getStreet();
    String getPostalCode();
    String getCity();

    LocalDate getBirthday();
    /**
     * @return birthday date-formatted as string
     */
    default String birthdayString() {
        if (getBirthday() == null) return "";
        return DateTimeUtil.format(getBirthday());
    }

    /**
     * @return unmodifiable list view of tags.
     */
    List<Tag> getTagList();
    /**
     * @return string representation of this Person's tags
     */
    default String tagsString() {
        final StringBuffer buffer = new StringBuffer();
        final String separator = ", ";
        getTagList().forEach(tag -> buffer.append(tag).append(separator));
        if (buffer.length() == 0) {
            return "";
        } else {
            return buffer.substring(0, buffer.length() - separator.length());
        }
    }

    default boolean dataFieldsEqual(ReadOnlyPerson other) {
        final Set<Tag> othersTags = new HashSet<>(other.getTagList());
        return fullName().equals(other.fullName())
                && getGithubUsername().equals(other.getGithubUsername())
                && getStreet().equals(other.getStreet())
                && getPostalCode().equals(other.getPostalCode())
                && getCity().equals(other.getCity())
                && getBirthday().equals(other.getBirthday())
                && getTagList().stream().allMatch(othersTags::contains);
    }

//// Operations below are optional; override if they will be needed.
//// Eg. implementing a simple person data object should not require using javafx classes like Property, so the
//// below methods make no sense for that context.

    default ReadOnlyStringProperty firstNameProperty() {
        throw new UnsupportedOperationException();
    }
    default ReadOnlyStringProperty lastNameProperty() {
        throw new UnsupportedOperationException();
    }
    default ReadOnlyStringProperty githubUsernameProperty() {
        throw new UnsupportedOperationException();
    }

    default ReadOnlyStringProperty streetProperty() {
        throw new UnsupportedOperationException();
    }
    default ReadOnlyStringProperty postalCodeProperty() {
        throw new UnsupportedOperationException();
    }
    default ReadOnlyStringProperty cityProperty() {
        throw new UnsupportedOperationException();
    }

    default ReadOnlyObjectProperty<LocalDate> birthdayProperty() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return ObservableList unmodifiable view of this Person's tags
     */
    default UnmodifiableObservableList<Tag> getObservableTagList() {
        throw new UnsupportedOperationException();
    }

    @Override
    default Observable[] extractObservables() {
        return new Observable[] {
                firstNameProperty(),
                lastNameProperty(),
                githubUsernameProperty(),

                streetProperty(),
                postalCodeProperty(),
                cityProperty(),

                birthdayProperty(),
                getObservableTagList()
        };
    }

    static List<Tag> getCommonTags(Collection<ReadOnlyPerson> persons) {
        Set<Tag> tags = new HashSet<>();
        persons.stream().forEach(p -> tags.addAll(p.getTagList()));
        List<Tag> assignedTags = tags.stream().filter(tag ->
                persons.stream()
                        .filter(p -> p.getObservableTagList().contains(tag))
                        .count() == persons.size())
                .collect(Collectors.toCollection(ArrayList::new));
        return assignedTags;
    }

    boolean hasName(String firstName, String lastName);
}
