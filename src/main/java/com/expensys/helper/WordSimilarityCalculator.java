package com.expensys.helper;

import org.apache.commons.text.similarity.CosineSimilarity;
import java.util.*;

public class WordSimilarityCalculator {
    private int wordLength;

    public WordSimilarityCalculator(int wordLength) {
        this.wordLength = wordLength;
    }

    public static void main(String[] args) {
//        System.out.println(calculateSimilarity("vegetable & fruits", "vegetables and fruits"));
        System.out.println(calculateSimilarity("fruit", "fruits"));
    }

    public static double calculateSimilarity(String word1, String word2) {
        // Tokenization
        List<String> word1List = tokenizeWord(word1);
        List<String> word2List = tokenizeWord(word2);

        // Remove stopwords
        List<String> word1Set = removeStopWords(word1List);
        List<String> word2Set = removeStopWords(word2List);

        // Convert the lists to maps with word frequencies
        Map<CharSequence, Integer> word1Map = createWordFrequencyMap(word1Set);
        Map<CharSequence, Integer> word2Map = createWordFrequencyMap(word2Set);

        // Calculate cosine similarity
        CosineSimilarity cosineSimilarity = new CosineSimilarity();
        double similarity = cosineSimilarity.cosineSimilarity(word1Map, word2Map);

        return similarity;
    }

    private static List<String> tokenizeWord(String word) {
        List<String> tokenList = new ArrayList<>();
        String[] tokens = word.split("\\s+"); // Split by whitespace
        for (String token : tokens) {
            tokenList.add(token.toLowerCase());
        }
        return tokenList;
    }

    private static List<String> removeStopWords(List<String> tokens) {
        List<String> cleanedTokens = new ArrayList<>();
        Set<String> stopWords = getStopWords(); // Define your set of stopwords

        for (String token : tokens) {
            if (!stopWords.contains(token)) {
                cleanedTokens.add(token);
            }
        }

        return cleanedTokens;
    }

    private static Map<CharSequence, Integer> createWordFrequencyMap(List<String> words) {
        Map<CharSequence, Integer> wordFrequencyMap = new HashMap<>();
        for (String word : words) {
            wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
        }
        return wordFrequencyMap;
    }

    private static Set<String> getStopWords() {
        // Define your set of English stopwords here
        Set<String> stopWords = new HashSet<>();
        stopWords.add("the");
        stopWords.add("and");
        stopWords.add("&");
        // Add more stopwords as needed
        return stopWords;
    }
}
