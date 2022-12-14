package com.coworkingspace.backend.common.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.difflib.text.DiffRow;
import com.github.difflib.text.DiffRowGenerator;

public final class StringDiffUtil {
	private StringDiffUtil() {
	}

	public static List<String> getStringDiff(List<String> oldStrings,
	                                         List<String> newStrings,
	                                         DiffRowGenerator diffRowGenerator) {
		if (oldStrings.equals(newStrings)) {
			return null;
		}

		return diffRowGenerator.generateDiffRows(
				oldStrings,
				newStrings
		).stream().map(DiffRow::getOldLine).collect(Collectors.toList());
	}

	public static List<String> setDiffLines(List<String> diffLines) {
		if (diffLines == null) {
			return null;
		}

		boolean checkNew = true;
		boolean checkOld = true;
		boolean checkBreakline = true;

		for (int i = 0; i < diffLines.size(); i++) {
			String endSpan = "</span>";
			String newSpan = "<span class=\"editNewInline\">";
			String oldSpan = "<span class=\"editOldInline\">";

			diffLines.set(i, diffLines.get(i).trim());
			String diffLine = diffLines.get(i);

			if ((diffLine.equals("") || diffLine.isBlank()) && i > 0) {
				if (!diffLines.get(i - 1).endsWith(oldSpan + endSpan) && checkBreakline) {
					diffLines.set(i, "<br>");
					checkBreakline = true;
				} else if (diffLines.get(i - 1).endsWith(oldSpan)) {
					checkBreakline = false;
				}
			}

			if (diffLines.get(i).startsWith(endSpan)) {
				if (!checkNew) {
					diffLines.set(i, newSpan + diffLine);
					checkNew = true;
				}
				if (!checkOld) {
					diffLines.set(i, oldSpan + diffLine);
					checkOld = true;
				}
			}

			int countNewSpan = diffLines.get(i).split(newSpan, -1).length - 1;
			int countSpan = diffLines.get(i).split(endSpan, -1).length - 1;
			int countOldSpan = diffLines.get(i).split(oldSpan, -1).length - 1;

			if (countNewSpan + countOldSpan > countSpan) {
				diffLines.set(i, diffLine + endSpan);
				if (countNewSpan > countOldSpan) {
					checkNew = false;
				} else if (countOldSpan > countNewSpan) {
					checkOld = false;
				}
				if (diffLines.get(i).indexOf(oldSpan) < diffLines.get(i).indexOf(newSpan)) {
					checkNew = false;
				} else {
					checkOld = false;
				}
			}

			if (countSpan > countOldSpan + countNewSpan) {
				if (!checkNew) {
					diffLines.set(i, newSpan + diffLine);
					checkNew = true;
				} else if (!checkOld) {
					diffLines.set(i, oldSpan + diffLine);
					checkOld = true;
				}
			} else if (countNewSpan == 0 && countOldSpan == 0 && countSpan == 0 && !diffLines.get(i).equals("<br>")) {
				if (!checkNew) {
					diffLines.set(i, newSpan + diffLine + endSpan);
				} else if (!checkOld) {
					diffLines.set(i, oldSpan + diffLine + endSpan);
				}
			}
		}

		return diffLines;
	}

	public static List<String> getDiffSentences(List<String> oldSentences, List<String> newSentences) {
		Set<String> similar = new HashSet<>(oldSentences);
		Set<String> different = new HashSet<>();
		different.addAll( oldSentences );
		different.addAll( newSentences );

		similar.retainAll( newSentences );
		different.removeAll( similar );

		List<String> result = new ArrayList<>(similar);

		Set<String> oldDiffSentences = new HashSet<>(different);
		oldDiffSentences.retainAll(oldSentences);
		oldDiffSentences.forEach(s -> result.add("<span class=\"editOldInline\">" + s + "</span>"));

		Set<String> newDiffSentences = new HashSet<>(different);
		newDiffSentences.retainAll(newSentences);
		newDiffSentences.forEach(s -> result.add("<span class=\"editNewInline\">" + s + "</span>"));

		if (oldDiffSentences.isEmpty() && newDiffSentences.isEmpty()){
			return null;
		}

		return result;
	}
}
