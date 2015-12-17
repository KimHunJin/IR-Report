package ttttest;

import java.util.LinkedList;

import kr.ac.kaist.swrc.jhannanum.comm.Eojeol;
import kr.ac.kaist.swrc.jhannanum.comm.Sentence;
import kr.ac.kaist.swrc.jhannanum.hannanum.Workflow;
import kr.ac.kaist.swrc.jhannanum.hannanum.WorkflowFactory;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Workflow workflow = WorkflowFactory.getPredefinedWorkflow(WorkflowFactory.WORKFLOW_NOUN_EXTRACTOR);
		try {
			workflow.activateWorkflow(true);
			String document = "한나눔 예제 입니다.";
			workflow.analyze(document);
			
			LinkedList<Sentence> resultList = workflow.getResultOfDocument(new Sentence(0, 0, false));
			for (Sentence sentence : resultList) {
				Eojeol[] eojeolArray = sentence.getEojeols();
				for(int i=0;i<eojeolArray.length;i++) {
					if(eojeolArray[i].length>0) {
						String[] ab = eojeolArray[i].getMorphemes();
						for(int j=0;j<ab.length;j++) {
							System.out.println(ab[j] + " ");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		workflow.close();

	}

}