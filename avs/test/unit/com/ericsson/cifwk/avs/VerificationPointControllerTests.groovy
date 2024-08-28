package com.ericsson.cifwk.avs



import org.junit.*
import grails.test.mixin.*

@TestFor(VerificationPointController)
@Mock(VerificationPoint)
class VerificationPointControllerTests {


    def populateValidParams(params) {
      assert params != null
      // TODO: Populate valid properties like...
      //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/verificationPoint/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.verificationPointInstanceList.size() == 0
        assert model.verificationPointInstanceTotal == 0
    }

    void testCreate() {
       def model = controller.create()

       assert model.verificationPointInstance != null
    }

    void testSave() {
        controller.save()

        assert model.verificationPointInstance != null
        assert view == '/verificationPoint/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/verificationPoint/show/1'
        assert controller.flash.message != null
        assert VerificationPoint.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/verificationPoint/list'


        populateValidParams(params)
        def verificationPoint = new VerificationPoint(params)

        assert verificationPoint.save() != null

        params.id = verificationPoint.id

        def model = controller.show()

        assert model.verificationPointInstance == verificationPoint
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/verificationPoint/list'


        populateValidParams(params)
        def verificationPoint = new VerificationPoint(params)

        assert verificationPoint.save() != null

        params.id = verificationPoint.id

        def model = controller.edit()

        assert model.verificationPointInstance == verificationPoint
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/verificationPoint/list'

        response.reset()


        populateValidParams(params)
        def verificationPoint = new VerificationPoint(params)

        assert verificationPoint.save() != null

        // test invalid parameters in update
        params.id = verificationPoint.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/verificationPoint/edit"
        assert model.verificationPointInstance != null

        verificationPoint.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/verificationPoint/show/$verificationPoint.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        verificationPoint.clearErrors()

        populateValidParams(params)
        params.id = verificationPoint.id
        params.version = -1
        controller.update()

        assert view == "/verificationPoint/edit"
        assert model.verificationPointInstance != null
        assert model.verificationPointInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/verificationPoint/list'

        response.reset()

        populateValidParams(params)
        def verificationPoint = new VerificationPoint(params)

        assert verificationPoint.save() != null
        assert VerificationPoint.count() == 1

        params.id = verificationPoint.id

        controller.delete()

        assert VerificationPoint.count() == 0
        assert VerificationPoint.get(verificationPoint.id) == null
        assert response.redirectedUrl == '/verificationPoint/list'
    }
}
