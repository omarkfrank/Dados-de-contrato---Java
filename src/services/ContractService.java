package services;

import java.time.LocalDate;

import entities.Contract;
import entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePayment;

	public ContractService(OnlinePaymentService onlinePayment) {
		this.onlinePayment = onlinePayment;
	}

	public void processContract(Contract contract, int months) {

		double basicQuota = contract.getTotalValue() / months;

		for (int i = 1; i <= months; i++) {
			LocalDate dueDate = contract.getDate().plusMonths(i);

			double interest = onlinePayment.interest(basicQuota, i);
			double fee = onlinePayment.paymentFee(basicQuota + interest);
			double quota = basicQuota + interest + fee;

			contract.getInstalments().add(new Installment(dueDate, quota));
		}
	}
}
