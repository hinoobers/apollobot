package org.hinoob.apollo.command;

import java.util.List;

public interface Command {

    String getName();
    List<String> getAliases();
}
