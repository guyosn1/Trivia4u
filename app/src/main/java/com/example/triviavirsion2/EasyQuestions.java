package com.example.triviavirsion2;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EasyQuestions {
    private static final List<Question> questions = new ArrayList<>();

    static {
        questions.add(new Question("What is the capital city of France?",
                new String[]{"London", "Rome", "Paris", "Madrid"}, 2));
        questions.add(new Question("Which planet is known as the Red Planet?",
                new String[]{"Venus", "Mars", "Jupiter", "Saturn"}, 1));
        questions.add(new Question("Who painted the Mona Lisa?",
                new String[]{"Pablo Picasso", "Vincent van Gogh", "Leonardo da Vinci", "Michelangelo"}, 2));
        questions.add(new Question("What is the largest ocean on Earth?",
                new String[]{"Indian Ocean", "Atlantic Ocean", "Arctic Ocean", "Pacific Ocean"}, 3));
        questions.add(new Question("How many continents are there?",
                new String[]{"Five", "Six", "Seven", "Eight"}, 2));
        questions.add(new Question("What is the main ingredient in guacamole?",
                new String[]{"Tomato", "Onion", "Avocado", "Pepper"}, 2));
        questions.add(new Question("Which animal is known as the King of the Jungle?",
                new String[]{"Elephant", "Tiger", "Lion", "Gorilla"}, 2));
        questions.add(new Question("What is the currency of Japan?",
                new String[]{"Won", "Yen", "Dollar", "Peso"}, 1));
        questions.add(new Question("Who wrote 'Romeo and Juliet'?",
                new String[]{"William Shakespeare", "Charles Dickens", "Mark Twain", "Jane Austen"}, 0));
        questions.add(new Question("What is the tallest mountain in the world?",
                new String[]{"K2", "Mount Kilimanjaro", "Mount Everest", "Mount Fuji"}, 2));
        questions.add(new Question("Which country is both an island and a continent?",
                new String[]{"Madagascar", "Greenland", "Australia", "Iceland"}, 2));
        questions.add(new Question("What is the smallest planet in our solar system?",
                new String[]{"Mars", "Pluto", "Mercury", "Venus"}, 2));
        questions.add(new Question("Which gas do plants absorb from the atmosphere for photosynthesis?",
                new String[]{"Oxygen", "Nitrogen", "Carbon dioxide", "Helium"}, 2));
        questions.add(new Question("What is the chemical symbol for water?",
                new String[]{"HO", "H₂O", "OH", "H₂O₂"}, 1));
        questions.add(new Question("Who was the first President of the United States?",
                new String[]{"Thomas Jefferson", "Abraham Lincoln", "George Washington", "John Adams"}, 2));
        questions.add(new Question("Which bird is known for its colorful tail feathers and courtship dance?",
                new String[]{"Swan", "Peacock", "Flamingo", "Parrot"}, 1));
        questions.add(new Question("What is the largest desert in the world?",
                new String[]{"Gobi", "Sahara Desert", "Arabian Desert", "Kalahari"}, 1));
        questions.add(new Question("Which fruit is known as the 'king of fruits'?",
                new String[]{"Mango", "Durian", "Banana", "Pineapple"}, 1));
        questions.add(new Question("What is the longest river in the world?",
                new String[]{"Amazon", "Yangtze", "Nile River", "Mississippi"}, 2));
        questions.add(new Question("Which country is known as the Land of the Rising Sun?",
                new String[]{"China", "South Korea", "Thailand", "Japan"}, 3));
    }

    public static Question getRandomQuestion() {
        Random rand = new Random();
        return questions.get(rand.nextInt(questions.size()));
    }

    public static List<Question> getRandomQuestions(int count) {
        List<Question> copy = new ArrayList<>(questions);
        Collections.shuffle(copy);
        return copy.subList(0, Math.min(count, copy.size()));
    }
}
