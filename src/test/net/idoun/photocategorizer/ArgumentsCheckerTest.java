package net.idoun.photocategorizer;

import static org.junit.Assert.assertFalse;

public class ArgumentsCheckerTest {

    public static final String[] ARGS_NO_CONTENTS = {};
    public static final String[] ARGS_SOURCE_NOT_EXIST_ONLY = {"SOURCE"};
    public static final String[] ARGS_SOURCE_NOT_EXIST_TARGET = {"SOURCE", "TARGET"};

    @org.junit.Test
    public void testIsNormal() throws Exception {
        ArgumentsChecker nullArgsChecker = new ArgumentsChecker(null, null);
        assertFalse(nullArgsChecker.isNormal());

        ArgumentsChecker noDirsChecker = new ArgumentsChecker(ARGS_SOURCE_NOT_EXIST_ONLY, null);
        assertFalse(noDirsChecker.isNormal());

        ArgumentsChecker noArgsChecker = new ArgumentsChecker(ARGS_NO_CONTENTS, new Directories());
        assertFalse(noArgsChecker.isNormal());

        ArgumentsChecker sourceNotExistChecker = new ArgumentsChecker(ARGS_SOURCE_NOT_EXIST_ONLY, new Directories());
        assertFalse(sourceNotExistChecker.isNormal());

        ArgumentsChecker sourceTargetNotExistChecker = new ArgumentsChecker(ARGS_SOURCE_NOT_EXIST_TARGET, new Directories());
        assertFalse(sourceTargetNotExistChecker.isNormal());
    }
}