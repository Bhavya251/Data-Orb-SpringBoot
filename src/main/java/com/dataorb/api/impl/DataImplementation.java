package com.dataorb.api.impl;

import com.dataorb.api.EmployeeRecord;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DataImplementation implements DataService{

    List<EmployeeRecord> records = new ArrayList<>();

    @Override
    public void processData(List<String> fileLines) {
        for(String line : fileLines){
            String[] parts = line.split(",");
            if (parts.length == 9) {
                records.add(new EmployeeRecord(
                        Integer.parseInt(parts[0].trim()),
                        parts[1].trim(),
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim(),
                        parts[6].trim(),
                        parts[7].trim(),
                        parts[8].trim()
                ));
            } else if (parts.length == 6) { // For events other than ONBOARD
                records.add(new EmployeeRecord(
                        Integer.parseInt(parts[0].trim()),
                        parts[1].trim(),
                        null,
                        null,
                        null,
                        parts[2].trim(),
                        parts[3].trim(),
                        parts[4].trim(),
                        parts[5].trim()
                ));
            } else {
                break;
            }
        }
    }

    @Override
    public Map<String, Object> getRecords() {
        Map<String, Object> reports = new LinkedHashMap<>();

        if(totalEmployees(records) == 0){
            return null;
        }

        //Task-1
        reports.put("1. Total number of employees: ",totalEmployees(records));

        //Task-2 - A
        reports.put("2-A. Monthly Joins: ", monthlyJoins(records));

        //Task-2 - B
        reports.put("2-B. Monthly Exits: ", monthlyExits(records));

        //Task-3
        reports.put("3. Monthly Salary: ", monthlySalary(records));

        //Task-4
        reports.put("4. Employee Financial Report: ", employeeFinancialReport(records));

        //Task-5
        reports.put("5. Monthly Amount: ", monthlyAmount(records));

        //Task-6
        reports.put("6. Yearly Financial Report: ", yearlyFinancialReport(records));



        return reports;
    }

    //Task-1
    public static Long totalEmployees(List<EmployeeRecord> records) {
        return records.stream().map(EmployeeRecord::getEmpID).distinct().count();
    }

    //Task-2-A
    public static Map<String, List<EmployeeRecord>> monthlyJoins(List<EmployeeRecord> records){

        Map<String, List<EmployeeRecord>> joins = records.stream()
                .filter(record -> record.getEvent().equals("ONBOARD"))
                .collect(Collectors.groupingBy(record -> record.getEventDate().substring(3)));

        return joins;
    }

    //Task-2-B
    public static Map<String, List<String>> monthlyExits(List<EmployeeRecord> records){
        Map<String, List<String>> monthlyExits = new LinkedHashMap<>();


        Map<String, List<EmployeeRecord>> exits = records.stream()
                .filter(record -> record.getEvent().equals("EXIT"))
                .collect(Collectors.groupingBy(record -> record.getEventDate().substring(3)));

        Map<String, String> empNames = records.stream()
                .filter(record -> record.getEvent().equals("ONBOARD"))
                .collect(Collectors.toMap(
                        EmployeeRecord::getEmpID,
                        record -> record.getEmpFName() + " " + record.getEmpLName()
                ));

        for(String month : exits.keySet()){
            monthlyExits.put("Month: " + month + "-> Total Exits: " + exits.get(month).size(),
                    exits.get(month).stream()
                    .map(record -> record.getEmpID() + ": " + empNames.get(record.getEmpID()))
                    .collect(Collectors.toList())
            );
        }
        return monthlyExits;
    }

    //Task-3
    public static List<String> monthlySalary(List<EmployeeRecord> records){
        List<String> monthlySalary = new ArrayList<>();

        Map<String, Double> totalSalaries = records.stream()
                .filter(record -> record.getEvent().equals("SALARY"))
                .collect(Collectors.groupingBy(
                        record -> record.getEventDate().substring(3),
                        Collectors.summingDouble(record -> Double.parseDouble(record.getValue()))
                ));

        Map<String, Long> employeeCount = records.stream()
                .filter(record -> record.getEvent().equals("SALARY"))
                .collect(Collectors.groupingBy(
                        record -> record.getEventDate().substring(3),
                        Collectors.counting()
                ));

        for (String month : totalSalaries.keySet()) {
            monthlySalary.add("Month: " + month + ", Total Salary: " + totalSalaries.get(month) +
                    ", Total Employees: " + employeeCount.get(month));
        }
        return monthlySalary;
    }

    //Task-4
    public static List<String> employeeFinancialReport(List<EmployeeRecord> records){
        List<String> employeeFinancialReport = new ArrayList<>();

        Map<String, Integer> totalPayments = records.stream()
                .filter(record -> !record.getEvent().equals("ONBOARD") && !record.getEvent().equals("EXIT"))
                .collect(Collectors.groupingBy(
                        EmployeeRecord::getEmpID,
                        Collectors.summingInt(record -> Integer.parseInt(record.getValue()))
                ));

        Map<String, String> empNames = records.stream()
                .filter(record -> record.getEvent().equals("ONBOARD"))
                .collect(Collectors.toMap(
                        EmployeeRecord::getEmpID,
                        record -> record.getEmpFName() + " " + record.getEmpLName()
                ));

        for (String empId : totalPayments.keySet()) {
            employeeFinancialReport.add("Employee ID: " + empId + ", Name: " + empNames.get(empId) +
                    ", Total Paid: " + totalPayments.get(empId));
        }
        return employeeFinancialReport;
    }

    //Task-5
    public static List<String> monthlyAmount(List<EmployeeRecord> records){
        List<String> monthlyAmount = new ArrayList<>();

        Map<String, Double> totalAmount = records.stream()
                .filter(record -> !record.getEvent().equals("ONBOARD") && !record.getEvent().equals("EXIT"))
                .collect(Collectors.groupingBy(
                        record -> record.getEventDate().substring(3),
                        Collectors.summingDouble(record -> Double.parseDouble(record.getValue()))
                ));

        Map<String, Long> empCount = records.stream()
                .filter(record -> !record.getEvent().equals("ONBOARD") && !record.getEvent().equals("EXIT"))
                .collect(Collectors.groupingBy(
                        record -> record.getEventDate().substring(3),
                        Collectors.counting()
                ));

        for (String month : totalAmount.keySet()) {
            monthlyAmount.add("Month: " + month + ", Total Amount Released: " + totalAmount.get(month) +
                    ", Total Employees: " + empCount.get(month));
        }
        return monthlyAmount;
    }

    //Task-6
    public static Map<String, Object> yearlyFinancialReport(List<EmployeeRecord> records){
        Map<String, Object> yearlyFinancialReport = new LinkedHashMap<>();

        Map<Integer, List<EmployeeRecord>> yearlyReports = records.stream()
                .collect(Collectors.groupingBy(
                        record -> Integer.parseInt(record.getEventDate().substring(6))
                ));

        for (Integer year : yearlyReports.keySet()) {
            yearlyFinancialReport.put("Year: " + year,
                    yearlyReports.get(year).stream()
                    .map(record -> "  Event: " + record.getEvent()
                            + ", EmpId: " + record.getEmpID()
                            + ", EventDate: " + record.getEventDate() + ", Value: " + record.getValue())
                            .collect(Collectors.toList())
            );
//            for (EmployeeRecord record : yearlyReports.get(year)) {
//                yearlyFinancialReport.add("  Event: " + record.getEvent() + ", EmpId: " + record.getEmpID() +
//                        ", EventDate: " + record.getEventDate() + ", Value: " + record.getValue());
//            }
        }
        return yearlyFinancialReport;
    }
}
