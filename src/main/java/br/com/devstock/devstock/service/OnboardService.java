package br.com.devstock.devstock.service;

import br.com.devstock.devstock.repository.*;
import org.springframework.stereotype.Service;

@Service
public class OnboardService {

    private final OnboardRepository onboardRepository;
    private final OnboardItemRepository onboardItemRepository;
    private final DesenvolvedorRepository desenvolvedorRepository;
    private final KitRepository kitRepository;
    private final NotebookRepository notebookRepository;

    public OnboardService(
            OnboardRepository onboardRepository,
            OnboardItemRepository onboardItemRepository,
            DesenvolvedorRepository desenvolvedorRepository,
            KitRepository kitRepository,
            NotebookRepository notebookRepository
    )
    {
        this.onboardRepository = onboardRepository;
        this.onboardItemRepository = onboardItemRepository;
        this.desenvolvedorRepository = desenvolvedorRepository;
        this.kitRepository = kitRepository;
        this.notebookRepository = notebookRepository;
    }

}
