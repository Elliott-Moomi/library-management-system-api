package library.management.system.service;

import jakarta.persistence.EntityNotFoundException;
import library.management.system.domain.Member;
import library.management.system.repository.BookRepository;
import library.management.system.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private final BookRepository bookRepository;

    public MemberService(MemberRepository memberRepository, BookRepository bookRepository) {
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member getMember(UUID id) {
        return memberRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Member with id: " + id + " does not exist"));

    }

    @Transactional
    public Member addMember(final Member member) {

        return memberRepository.save(member);

    }

    @Transactional
    public Member updateMember(final UUID id, final Member updatedMember) {
        final var member = getMember(id);

        member.setFirstName(updatedMember.getFirstName());
        member.setLastName(updatedMember.getLastName());
        member.setEmail(updatedMember.getEmail());
        member.setAge(updatedMember.getAge());
        member.setContact(updatedMember.getContact());

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public void removeMember(final UUID id) {
        final var member = getMember(id);
        memberRepository.delete(member);
    }
}
