use sistemas_distribuidos;

ALTER TABLE skills
ADD FOREIGN KEY (idcandidate) REFERENCES candidate(idcandidate);

ALTER TABLE skills
ADD FOREIGN KEY (idskills_dataset) REFERENCES skills_dataset(idskills_dataset);

ALTER TABLE job_dataset
ADD FOREIGN KEY (idrecruiter) REFERENCES recruiter(idrecruiter);