package com.chotix.mydiary1.shared.gui;

import com.chotix.mydiary1.contacts.ContactsEntity;

import java.util.Comparator;

public class LetterComparator implements Comparator<ContactsEntity> {

    @Override
    public int compare(ContactsEntity lhs, ContactsEntity rhs) {
        return lhs.getSortLetters().compareTo(rhs.getSortLetters());

    }
    //comparator 用于比较两个对象
}
