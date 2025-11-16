package com.example.lastquiz;

import com.example.lastquiz.entity.*;
import com.example.lastquiz.repository.AdminRepository;
import com.example.lastquiz.repository.SubscriptionRepository;
import com.example.lastquiz.service.ComplaintService;
import com.example.lastquiz.service.UserManagementService;
import com.example.lastquiz.service.QuizService;
import com.example.lastquiz.service.QuizValidationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class LastquizApplication implements CommandLineRunner {

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private QuizService quizService;

    @Autowired
    private QuizValidationService quizValidationService;

    public static void main(String[] args) {
        SpringApplication.run(LastquizApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1) Gérer les utilisateurs");
            System.out.println("2) Gérer les subscriptions");
            System.out.println("3) Gérer les réclamations");
            System.out.println("4) Gérer les quiz");  // 🔹 AJOUTÉ
            System.out.println("0) Quitter");
            System.out.print("Choisissez une option : ");
            String mainChoice = scanner.nextLine().trim();

            switch (mainChoice) {
                case "1" -> manageUsers(scanner);
                case "2" -> manageSubscriptions(scanner);
                case "3" -> manageComplaints(scanner);
                case "4" -> manageQuizzes(scanner);    // 🔹 AJOUTÉ
                case "0" -> {
                    running = false;
                    System.out.println("Fin du programme.");
                }
                default -> System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }

    // ====================== 🔹 MENU UTILISATEURS ======================
    private void manageUsers(Scanner scanner) {
        System.out.println("\n=== MENU UTILISATEUR ===");
        System.out.println("1 - Créer un utilisateur");
        System.out.println("2 - Modifier un utilisateur");
        System.out.println("3 - Supprimer un utilisateur");
        System.out.print("Choisissez une option : ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> createUserConsole(scanner);
            case "2" -> updateUserConsole(scanner);
            case "3" -> deleteUserConsole(scanner);
            default -> System.out.println("Choix invalide.");
        }
    }

    private void createUserConsole(Scanner scanner) {
        System.out.print("Nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine();
        System.out.print("Rôle (STUDENT/PROFESSOR/ADMIN) : ");
        String roleStr = scanner.nextLine().toUpperCase();

        User.Role role;
        try {
            role = User.Role.valueOf(roleStr);
        } catch (Exception e) {
            System.out.println("Rôle invalide, utilisation de STUDENT par défaut.");
            role = User.Role.STUDENT;
        }

        System.out.print("Prénom : ");
        String firstName = scanner.nextLine();
        System.out.print("Nom : ");
        String lastName = scanner.nextLine();

        String extraInfoPrompt = switch (role) {
            case STUDENT -> "Niveau de l'étudiant : ";
            case PROFESSOR -> "Spécialité du professeur : ";
            case ADMIN -> "Département de l'admin : ";
        };

        System.out.print(extraInfoPrompt);
        String extraInfo = scanner.nextLine();

        User user = User.builder()
                .username(username)
                .email(email)
                .password(password)
                .role(role)
                .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                .build();

        userManagementService.createUser(user, firstName, lastName, extraInfo);
        System.out.println("✅ Utilisateur créé avec succès !");
    }

    private void updateUserConsole(Scanner scanner) {
        System.out.print("Entrez l'ID de l'utilisateur à modifier : ");
        Integer id = readInt(scanner);
        if (id == null) return;

        System.out.print("Nouveau nom d'utilisateur (laisser vide pour ne pas changer) : ");
        String username = scanner.nextLine();
        if (username.isBlank()) username = null;

        System.out.print("Nouvel email (laisser vide pour ne pas changer) : ");
        String email = scanner.nextLine();
        if (email.isBlank()) email = null;

        System.out.print("Nouveau mot de passe (laisser vide pour ne pas changer) : ");
        String password = scanner.nextLine();
        if (password.isBlank()) password = null;

        try {
            userManagementService.updateUser(id, username, email, password);
            System.out.println("📝 Utilisateur modifié avec succès !");
        } catch (RuntimeException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private void deleteUserConsole(Scanner scanner) {
        System.out.print("Entrez l'ID de l'utilisateur à supprimer : ");
        Integer id = readInt(scanner);
        if (id == null) return;

        try {
            userManagementService.deleteUser(id);
            System.out.println("🗑️ Utilisateur supprimé avec succès !");
        } catch (RuntimeException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    // ====================== 🔹 MENU SUBSCRIPTIONS ======================
    private void manageSubscriptions(Scanner scanner) {
        System.out.println("\n=== MENU SUBSCRIPTION ===");
        System.out.println("1 - Créer une subscription");
        System.out.println("2 - Modifier une subscription (par ID)");
        System.out.println("3 - Supprimer une subscription (par ID)");
        System.out.print("Choisissez une option : ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> createSubscription(scanner);
            case "2" -> updateSubscriptionById(scanner);
            case "3" -> deleteSubscriptionById(scanner);
            default -> System.out.println("Choix invalide.");
        }
    }

    private void createSubscription(Scanner scanner) {
        System.out.print("Nom de la subscription : ");
        String name = scanner.nextLine().trim();
        if (name.isBlank()) {
            System.out.println("Nom obligatoire.");
            return;
        }

        System.out.print("Prix (ex: 9.99) : ");
        Double price = readDouble(scanner);
        if (price == null) return;

        System.out.print("Durée en jours (ex: 30) : ");
        Integer durationDays = readInt(scanner);
        if (durationDays == null) return;

        Subscription s = Subscription.builder()
                .name(name)
                .price(price)
                .durationDays(durationDays)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();

        subscriptionRepository.save(s);
        System.out.println("✅ Subscription créée : " + s);
    }

    private void updateSubscriptionById(Scanner scanner) {
        System.out.print("Entrez l'ID de la subscription à modifier : ");
        Integer id = readInt(scanner);
        if (id == null) return;

        Optional<Subscription> opt = subscriptionRepository.findById(id);
        if (opt.isEmpty()) {
            System.out.println("❌ Aucune subscription trouvée avec l'ID : " + id);
            return;
        }
        Subscription s = opt.get();

        System.out.print("Nouveau nom (" + s.getName() + ") : ");
        String newName = scanner.nextLine();
        if (!newName.isBlank()) s.setName(newName);

        System.out.print("Nouveau prix (" + s.getPrice() + ") : ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isBlank()) {
            try {
                s.setPrice(Double.parseDouble(priceStr));
            } catch (NumberFormatException e) {
                System.out.println("Prix invalide, inchangé.");
            }
        }

        System.out.print("Nouvelle durée en jours (" + s.getDurationDays() + ") : ");
        String durStr = scanner.nextLine();
        if (!durStr.isBlank()) {
            try {
                s.setDurationDays(Integer.parseInt(durStr));
            } catch (NumberFormatException e) {
                System.out.println("Durée invalide, inchangée.");
            }
        }

        subscriptionRepository.save(s);
        System.out.println("📝 Subscription modifiée : " + s);
    }

    private void deleteSubscriptionById(Scanner scanner) {
        System.out.print("Entrez l'ID de la subscription à supprimer : ");
        Integer id = readInt(scanner);
        if (id == null) return;

        Optional<Subscription> opt = subscriptionRepository.findById(id);
        if (opt.isEmpty()) {
            System.out.println("❌ Aucune subscription trouvée avec l'ID : " + id);
            return;
        }
        Subscription s = opt.get();

        System.out.print("Confirmez la suppression de '" + s.getName() + "' (oui/non) : ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if ("oui".equals(confirm) || "o".equals(confirm) || "yes".equals(confirm)) {
            subscriptionRepository.delete(s);
            System.out.println("🗑️ Subscription supprimée : " + s.getName());
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    // ====================== 🔹 MENU RECLAMATIONS ======================
    private void manageComplaints(Scanner scanner) {
        System.out.println("\n=== MENU RÉCLAMATIONS ===");
        System.out.println("1 - Afficher toutes les réclamations");
        System.out.println("2 - Modifier le statut d'une réclamation");
        System.out.println("3 - Répondre à une réclamation");
        System.out.print("Choisissez une option : ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> viewAllComplaints();
            case "2" -> updateComplaintStatus(scanner);
            case "3" -> respondToComplaint(scanner);
            default -> System.out.println("Choix invalide.");
        }
    }

    private void viewAllComplaints() {
        List<Complaint> complaints = complaintService.getAllComplaints();
        if (complaints.isEmpty()) {
            System.out.println("Aucune réclamation trouvée.");
            return;
        }

        System.out.println("\n=== LISTE DES RÉCLAMATIONS ===");
        for (Complaint c : complaints) {
            System.out.printf(
                    "ID: %d | Étudiant: %s %s | Sujet: %s | Statut: %s%n",
                    c.getId(),
                    c.getStudent() != null ? c.getStudent().getFirstName() : "N/A",
                    c.getStudent() != null ? c.getStudent().getLastName() : "",
                    c.getSubject(),
                    c.getStatus()
            );
        }
    }

    private void updateComplaintStatus(Scanner scanner) {
        System.out.print("Entrez l'ID de la réclamation à modifier : ");
        Integer id = readInt(scanner);
        if (id == null) return;

        System.out.println("Statuts : PENDING, IN_PROGRESS, RESOLVED, REJECTED");
        System.out.print("Nouveau statut : ");
        String statusStr = scanner.nextLine().trim().toUpperCase();

        try {
            Complaint.Status newStatus = Complaint.Status.valueOf(statusStr);
            complaintService.updateStatus(id, newStatus);
            System.out.println("✅ Statut mis à jour !");
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Statut invalide !");
        }
    }

    private void respondToComplaint(Scanner scanner) {
        System.out.print("ID de la réclamation : ");
        Integer id = readInt(scanner);
        if (id == null) return;

        System.out.print("ID de l'admin : ");
        Integer adminId = readInt(scanner);
        if (adminId == null) return;

        Optional<Admin> adminOpt = adminRepository.findById(adminId);
        if (adminOpt.isEmpty()) {
            System.out.println("❌ Admin introuvable !");
            return;
        }

        System.out.print("Réponse : ");
        String response = scanner.nextLine();

        complaintService.respondToComplaint(id, response, adminId, adminOpt.get());
        System.out.println("✅ Réponse enregistrée !");
    }

    // ====================== 🔹 MENU QUIZ (NOUVEAU) ======================
    private void manageQuizzes(Scanner scanner) {
        System.out.println("\n=== MENU QUIZ ===");
        System.out.println("1 - Afficher tous les quiz");
        System.out.println("2 - Valider un quiz");
        System.out.print("Choisissez une option : ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> viewAllQuizzes();
            case "2" -> validateQuizConsole(scanner);
            default -> System.out.println("Choix invalide.");
        }
    }

    private void viewAllQuizzes() {
        List<Quiz> quizzes = quizService.getAllQuizzes();

        if (quizzes.isEmpty()) {
            System.out.println("Aucun quiz trouvé.");
            return;
        }

        System.out.println("\n=== LISTE DES QUIZ ===");
        for (Quiz q : quizzes) {
            System.out.printf(
                    "ID: %d | Titre: %s | Professeur: %s %s | Code: %s | Durée: %d min%n",
                    q.getId(),
                    q.getTitle(),
                    q.getProfessor() != null ? q.getProfessor().getFirstName() : "N/A",
                    q.getProfessor() != null ? q.getProfessor().getLastName() : "",
                    q.getCode(),
                    q.getDuration()
            );
        }
    }

    private void validateQuizConsole(Scanner scanner) {
        System.out.print("Entrez l'ID du quiz à valider : ");
        Integer quizId = readInt(scanner);
        if (quizId == null) return;

        Quiz quiz = quizService.getQuizById(quizId);
        if (quiz == null) {
            System.out.println("❌ Quiz introuvable !");
            return;
        }

        System.out.print("Entrez l'ID de l'admin qui valide : ");
        Integer adminId = readInt(scanner);
        if (adminId == null) return;

        Admin admin = adminRepository.findById(adminId).orElse(null);
        if (admin == null) {
            System.out.println("❌ Admin introuvable !");
            return;
        }

        System.out.print("Statut de validation (VALIDÉ / REFUSÉ) : ");
        String status = scanner.nextLine().trim().toUpperCase();

        System.out.print("Commentaires (optionnels) : ");
        String comments = scanner.nextLine();

        QuizValidation validation = quizValidationService.validateQuiz(quiz, admin, status, comments);

        System.out.println("✅ Validation enregistrée avec succès !");
        System.out.println("Validation ID: " + validation.getId());
    }

    // ====================== 🔹 Helpers ======================
    private Integer readInt(Scanner scanner) {
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide (entier attendu).");
            return null;
        }
    }

    private Double readDouble(Scanner scanner) {
        String line = scanner.nextLine().trim();
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            System.out.println("Entrée invalide (nombre attendu).");
            return null;
        }
    }
}
